package com.unnatural.hytale.party.model;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Party {
    /**
     * PlayerRef#uuid that created the party
     */
    private final PlayerRef leader;
    private final Set<PlayerRef> members;

    public void addPlayer(PlayerRef member) {
        members.add(member);
    }

    public void removePlayer(PlayerRef member) {
        members.remove(member);
    }

    public boolean hasPlayer(PlayerRef member) {
        return members.contains(member);
    }

    public List<PlayerRef> getMembers() {
        return List.copyOf(this.members);

    }
}
