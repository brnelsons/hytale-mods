package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.unnatural.hytale.party.model.Party;

public interface PartyService {
    Party create(PlayerRef playerRef);

    void invite(PlayerRef playerRef, String playerName);

    boolean isInParty(PlayerRef playerRef);

    void join(PlayerRef playerRef, String playerName);

    void leave(PlayerRef playerRef);
}
