package com.unnatural.hytale.party.model;

import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.*;

public class Party {
    /**
     * PlayerRef#uuid that created the party
     */
    private final UUID uuid;
    private final Set<PlayerRef> players;

    public Party(UUID uuid) {
        this.uuid = uuid;
        this.players = new HashSet<>();
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void addPlayer(PlayerRef playerRef) {
        players.add(playerRef);
    }

    public void removePlayer(PlayerRef playerRef) {
        players.remove(playerRef);
    }

    public boolean hasPlayer(PlayerRef playerRef) {
        return players.contains(playerRef);
    }
}
