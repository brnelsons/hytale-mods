package com.unnatural.hytale.party.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class Party {
    /**
     * PlayerRef#uuid that created the party
     */
    private final UUID leaderUuid;
    private final Set<UUID> playerUuids;

    public Party(UUID leaderUuid) {
        this.leaderUuid = leaderUuid;
        this.playerUuids = new HashSet<>();
    }

    public UUID getLeaderUuid() {
        return this.leaderUuid;
    }

    public void addPlayer(UUID playerUuid) {
        playerUuids.add(playerUuid);
    }

    public void removePlayer(UUID playerUuid) {
        playerUuids.remove(playerUuid);
    }

    public boolean hasPlayer(UUID playerUuid) {
        return playerUuids.contains(playerUuid);
    }

    public Stream<UUID> getPlayerUuids() {
        return this.playerUuids.stream();

    }
}
