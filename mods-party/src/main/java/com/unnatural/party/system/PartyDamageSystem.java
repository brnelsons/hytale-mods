package com.unnatural.party.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.SystemGroup;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.AllLegacyLivingEntityTypesQuery;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.party.model.Party;
import com.unnatural.party.service.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class PartyDamageSystem extends DamageEventSystem {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private static final Query<EntityStore> QUERY = AllLegacyLivingEntityTypesQuery.INSTANCE;
    private final PartyService partyService;

    public PartyDamageSystem(PartyService partyService) {
        this.partyService = partyService;
    }

    @Nullable
    public SystemGroup<EntityStore> getGroup() {
        return DamageModule.get().getFilterDamageGroup();
    }

    @Override
    public void handle(int index,
                       @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
                       @NonNullDecl Store<EntityStore> store,
                       @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
                       @NonNullDecl Damage damage) {
        // get the player that took damage
        final PlayerRef victim = archetypeChunk.getComponent(index, PlayerRef.getComponentType());
        if (victim == null) return; // not a player
        // get the party for the victim
        final Optional<Party> victimParty = partyService.getPartyFor(victim.getUuid());
        if (victimParty.isEmpty()) return; // not in a party
        // get all party members and update their HUD
        // TODO assuming the player hasn't actually taken the damage yet this will always be one tick behind
        // TODO NPEs everywhere!!
        emitUpdateToAllPartyMembers(victimParty.get());
        // if the type of damage was entity source
        if (!(damage.getSource() instanceof Damage.EntitySource entitySource)) return;

        final Ref<EntityStore> attackerRef = entitySource.getRef();
        if (!attackerRef.isValid()) return;
        // get attacking player
        final PlayerRef attacker = commandBuffer.getComponent(attackerRef, PlayerRef.getComponentType());
        if (attacker == null) return;

        final Optional<Party> attackerParty = partyService.getPartyFor(attacker.getUuid());
        if (attackerParty.isEmpty()) return;

        if (!attackerParty.get().getUuid().equals(victimParty.get().getUuid())) return;

        // Cancel damage between party members
        damage.setCancelled(true);
    }

    @Override
    @Nullable
    public Query<EntityStore> getQuery() {
        return QUERY;
    }

    private void emitUpdateToAllPartyMembers(@NonNull Party victimParty) {
        victimParty.getMembers()
                .stream()
                .map(m -> Universe.get().getPlayer(m))
                .map(playerRef -> playerRef.getReference()
                        .getStore()
                        .getComponent(playerRef.getReference(), Player.getComponentType()))
                .forEach(p -> p.getHudManager().getCustomHud().update(false, new UICommandBuilder()));
    }
}
