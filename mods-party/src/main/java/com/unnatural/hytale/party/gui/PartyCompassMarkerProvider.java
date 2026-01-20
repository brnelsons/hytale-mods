package com.unnatural.hytale.party.gui;

import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
import com.hypixel.hytale.server.core.util.PositionUtil;
import com.unnatural.hytale.party.service.PartyService;

public class PartyCompassMarkerProvider implements WorldMapManager.MarkerProvider {

    private final PartyService partyService;

    public PartyCompassMarkerProvider(PartyService partyService) {
        this.partyService = partyService;
    }

    @Override
    public void update(World world,
                       GameplayConfig config,
                       WorldMapTracker tracker,
                       int viewRadius,
                       int chunkX,
                       int chunkY) {
        // TODO how to get playerRef another way?
        //noinspection removal
        partyService.findParty(tracker.getPlayer().getPlayerRef()).ifPresent(party -> {
            party.getMembers().forEach(memberUuid -> {
                PlayerRef memberRef = Universe.get().getPlayer(memberUuid);
                if (memberRef == null) return;
                if (!memberRef.isValid()) return;
                Transform transform = memberRef.getTransform();
                String username = memberRef.getUsername();
                String markerUuid = memberRef.getUuid().toString();
                MapMarker marker = new MapMarker(
                        markerUuid,
                        username,
                        "Player.png",
                        PositionUtil.toTransformPacket(transform),
                        null
                );
                tracker.trySendMarker(-1, chunkX, chunkY, marker);
            });
        });
    }
}
