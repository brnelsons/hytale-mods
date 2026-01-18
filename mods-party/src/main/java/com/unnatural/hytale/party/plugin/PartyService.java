package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.UUID;
import java.util.stream.Stream;

public interface PartyService {
    void createInvite(PlayerRef sourcePlayer, PlayerRef targetPlayer);

    void acceptInvite(PlayerRef targetPlayer);

    boolean hasInvite(PlayerRef targetPlayer);

    boolean isInParty(PlayerRef playerRef);

    Stream<UUID> getPlayersInPartyWith(PlayerRef playerRef);

    void leave(PlayerRef playerRef);
}
