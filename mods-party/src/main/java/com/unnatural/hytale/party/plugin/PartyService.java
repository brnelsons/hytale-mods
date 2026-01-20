package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.unnatural.hytale.party.model.Party;

import java.util.Optional;
import java.util.stream.Stream;

public interface PartyService {
    void createParty(Party party);

    void createInvite(PlayerRef sourcePlayer, PlayerRef targetPlayer);

    void acceptInvite(PlayerRef targetPlayer);

    boolean hasInvite(PlayerRef targetPlayer);

    boolean isInParty(PlayerRef playerRef);

    Optional<Party> getPartyForPlayer(PlayerRef playerRef);

    Stream<PlayerRef> getPartyMembersFor(PlayerRef playerRef);

    void leave(PlayerRef playerRef);
}
