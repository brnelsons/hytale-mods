package com.unnatural.party.hud;

import com.hypixel.hytale.logger.HytaleLogger;
import com.unnatural.party.service.PartyService;

public class PartyCompassMarkerProvider {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final PartyService partyService;

    public PartyCompassMarkerProvider(PartyService partyService) {
        this.partyService = partyService;
    }

    //    public void update(World world,
    //                       GameplayConfig config,
    //                       WorldMapTracker tracker,
    //                       int viewRadius,
    //                       int chunkX,
    //                       int chunkY) {
    //        // TODO how to get playerRef another way?
    //        //noinspection removal
    //        PlayerRef playerRef = tracker.getPlayer().getPlayerRef();
    //        Transform pTransform = playerRef.getTransform();
    //        pTransform.setPosition(pTransform.getPosition().add(10, 10, 10));
    //        MapMarker m = new MapMarker(
    //                playerRef.getUuid().toString(),
    //                "test",
    //                "Common/UI/Custom/CustomArrow.png",
    //                PositionUtil.toTransformPacket(pTransform),
    //                null
    //        );
    //        tracker.trySendMarker(-1, chunkX, chunkY, m);
    //        partyService.findParty(playerRef).ifPresent(party -> {
    //            party.getMembers().forEach(memberUuid -> {
    //                PlayerRef memberRef = Universe.get().getPlayer(memberUuid);
    //                if (memberRef == null) return;
    //                if (!memberRef.isValid()) return;
    //                Transform transform = memberRef.getTransform();
    //                String username = memberRef.getUsername();
    //                String markerUuid = memberRef.getUuid().toString();
    //                transform.setPosition(transform.getPosition().add(10, 10, 10));
    //                MapMarker marker = new MapMarker(
    //                        markerUuid,
    //                        username,
    //                        "Player.png",
    //                        PositionUtil.toTransformPacket(transform),
    //                        null
    //                );
    //                tracker.trySendMarker(-1, chunkX, chunkY, marker);
    //            });
    //        });
    //    }
}
