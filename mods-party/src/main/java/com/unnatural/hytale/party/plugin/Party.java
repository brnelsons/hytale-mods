package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.*;

public class Party {
    private final UUID uuid;
    private PlayerRef owner;
    private Set<PlayerRef> players;

    public Party(UUID uuid) {
        this.uuid = uuid;
        this.players = new HashSet<>();
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setOwner(PlayerRef playerRef) {
        this.owner = playerRef;
    }

    public void addPlayer(PlayerRef playerRef) {
        players.add(playerRef);
    }

    public void removePlayer(PlayerRef playerRef) {
        players.remove(playerRef);
    }
}
