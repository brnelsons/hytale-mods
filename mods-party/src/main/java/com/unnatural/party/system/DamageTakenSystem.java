package com.unnatural.party.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.dependency.Dependency;
import com.hypixel.hytale.component.dependency.Order;
import com.hypixel.hytale.component.dependency.SystemGroupDependency;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsSystems;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.Set;

@RequiredArgsConstructor
public class DamageTakenSystem extends DamageEventSystem implements EntityStatsSystems.StatModifyingSystem {

    private static final Query<EntityStore> QUERY = EntityStatMap.getComponentType();
    private static final Set<Dependency<EntityStore>> DEPENDENCIES;

    static {
        DEPENDENCIES = Set.of(new SystemGroupDependency(Order.AFTER, DamageModule.get().getInspectDamageGroup()));
    }

    private final PartyService partyService;

    public Query<EntityStore> getQuery() {
        return QUERY;
    }

    @Override
    @NonNull
    public Set<Dependency<EntityStore>> getDependencies() {
        return DEPENDENCIES;
    }

    @Override
    public void handle(int index,
                       @NonNull ArchetypeChunk<EntityStore> archetypeChunk,
                       @NonNull Store<EntityStore> store,
                       @NonNull CommandBuffer<EntityStore> commandBuffer,
                       @NonNull Damage damage) {
        PlayerRef playerRef = archetypeChunk.getComponent(index, PlayerRef.getComponentType());
        if (playerRef == null) return;
        emitUpdateToAllPartyMembers(playerRef);
    }

    private void emitUpdateToAllPartyMembers(@NonNull PlayerRef playerRef) {
        partyService.getPartyMembersFor(playerRef)
                .map(m -> Universe.get().getPlayer(m))
                .map(pRef -> pRef.getReference()
                        .getStore()
                        .getComponent(pRef.getReference(), Player.getComponentType()))
                .forEach(p -> p.getHudManager().getCustomHud().update(false, new UICommandBuilder()));
    }
}
