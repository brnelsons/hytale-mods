package com.unnatural.hytale.party.model;

import com.google.common.collect.Sets;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Party {

    public static Party create(PlayerRef leader) {
        return create(leader.getUuid());
    }

    public static Party create(UUID leaderUuid) {
        return new Party(leaderUuid, leaderUuid, Sets.newHashSet(leaderUuid));
    }

    /**
     * PlayerRef#uuid that created the party
     */
    private final UUID uuid;
    private final UUID leader;
    // TODO does this need to be thread safe?
    private final Set<UUID> members;

    public void addPlayer(UUID playerUuid) {
        members.add(playerUuid);
    }

    public void removePlayer(UUID playerUuid) {
        members.remove(playerUuid);
    }

    public boolean hasPlayer(UUID playerUuid) {
        return members.contains(playerUuid);
    }

    public List<UUID> getMembers() {
        return List.copyOf(this.members);

    }
}
