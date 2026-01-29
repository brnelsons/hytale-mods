package com.unnatural.party.gui;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.ui.Anchor;
import com.hypixel.hytale.server.core.ui.Value;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.party.service.PartyService;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * {@link com.hypixel.hytale.server.core.ui.Anchor}
 * {@link com.hypixel.hytale.server.core.ui.Area}
 * {@link com.hypixel.hytale.server.core.ui.PatchStyle}
 * {@link com.hypixel.hytale.server.core.ui.ItemGridSlot}
 * {@link com.hypixel.hytale.server.core.ui.DropdownEntryInfo}
 */
public class PartyHud extends CustomUIHud {
    private static final String DOCUMENT_REF = "PartyHud.ui";
    private static final String MEMBER_REF = "PartyMember.ui";

    private final PartyService partyService;
    private final ArrayList<PartyHudPlayerData> playerHudData;

    public PartyHud(@NonNull PlayerRef playerRef,
                    @NonNull PartyService partyService) {
        super(playerRef);
        this.partyService = partyService;
        this.playerHudData = new ArrayList<>();// TODO Concurrency??
    }

    @Override
    protected void build(@NonNull UICommandBuilder cmd) {
        cmd.append(DOCUMENT_REF);
        partyService.findParty(getPlayerRef()).ifPresent(party -> {
            List<PlayerRef> partyMembers = party.getMembers().stream()
                    // TODO should this get all players and access the map non-concurrently?
                    .map(playerUuid -> Universe.get().getPlayer(playerUuid))
                    .filter(Objects::nonNull)
                    .toList();
            setPartyMembers(cmd, party.getLeader(), partyMembers);
        });
    }

    @Override
    public void update(boolean clear, @NonNull UICommandBuilder cmd) {
        super.update(clear, cmd);
        partyService.findParty(getPlayerRef()).ifPresent(party -> {
            List<PlayerRef> partyMembers = party.getMembers().stream()
                    // TODO should this get all players and access the map non-concurrently?
                    .map(playerUuid -> Universe.get().getPlayer(playerUuid))
                    .filter(Objects::nonNull)
                    .toList();
            setPartyMembers(cmd, party.getLeader(), partyMembers);
        });
    }

    private void setPartyMembers(UICommandBuilder cmd, UUID leaderUuid, List<PlayerRef> members) {
        cmd.clear("#PartyMembers");
        playerHudData.clear();
        int i = 0;
        Transform selfTransform = getPlayerRef().getTransform();
        for (PlayerRef member : members) {
            if (!member.isValid()) continue;
            cmd.append("#PartyMembers", MEMBER_REF);
            Ref<EntityStore> ref = member.getReference();
            EntityStatMap entityStatMap = ref.getStore().getComponent(ref, EntityStatMap.getComponentType());
            EntityStatValue healthValue = entityStatMap.get(DefaultEntityStatTypes.getHealth());
            float currentHealth = healthValue.get();
            float maxHealth = healthValue.getMax();
            Direction dir = getDirectionToPlayer(selfTransform, member.getTransform());
            PartyHudPlayerData data = PartyHudPlayerData.builder()
                    .username(member.getUsername())
                    .isLeader(member.getUuid().equals(leaderUuid))
                    .healthPercent(currentHealth * 100 / maxHealth)
                    .direction(dir)
                    .build();
            playerHudData.add(data);
            updatePlayerData(cmd, data, i);
            i++;
        }
    }

    // TOOD onUpdatePlayerData

    private void updatePlayerData(UICommandBuilder cmd,
                                  PartyHudPlayerData data,
                                  int index) {
        final String selector = String.format("#PartyMembers[%d] ", index);
        if (data.isLeader()) {
            cmd.set(selector + "#Leader.Text", data.getUsername());
            cmd.set(selector + "#Leader.Visible", true);
        } else {
            cmd.set(selector + "#Name.Text", data.getUsername());
            cmd.set(selector + "#Name.Visible", true);
        }
        cmd.set(selector + "#Health.Text", String.format("[%.0f]", data.getHealthPercent()));
        if (data.getDirection() != null) {
            cmd.set(selector + "#Direction.Text", data.getDirection().getCharacter());
        }
        Anchor a = new Anchor();
        a.setWidth(Value.of(40));
        cmd.setObject(selector + "#HealthBar.Anchor", a);
    }

    private Direction getDirectionToPlayer(Transform self,
                                           Transform other) {
        Vector3d selfPos = self.getPosition();
        Vector3d otherPos = other.getPosition();
        double deltaX = otherPos.x - selfPos.x;
        double deltaY = otherPos.y - selfPos.y;
        //        double deltaZ = otherPos.z - selfPos.z;
        double atan = Math.atan2(deltaY, deltaX);
        final double split = 22.5f;
        double linearTan = atan + split;
        if (linearTan <= 45) {
            return Direction.NORTH;
        } else if (linearTan <= 90) {
            return Direction.NORTH_EAST;
        } else if (linearTan <= 135) {
            return Direction.EAST;
        } else if (linearTan <= 180) {
            return Direction.SOUTH_EAST;
        } else if (linearTan <= 225) {
            return Direction.SOUTH;
        } else if (linearTan <= 270) {
            return Direction.SOUTH_WEST;
        } else if (linearTan <= 315) {
            return Direction.WEST;
        } else if (linearTan <= 360) {
            return Direction.NORTH_WEST;
        }
        return Direction.NORTH;
    }
}
