package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.unnatural.hytale.common.storage.InMemoryStore;
import com.unnatural.hytale.common.storage.Store;
import com.unnatural.hytale.party.model.Party;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;

public class PartyServiceImpl implements PartyService {

    private final Store<PlayerRef, Party> store;
    private final Store<PlayerRef, PlayerRef> invites;

    public PartyServiceImpl(Store<PlayerRef, Party> store) {
        this.store = store;
        this.invites = new InMemoryStore<>();
    }

    @Override
    public void createParty(Party party) {
        store.save(party.getLeader(), party);
    }

    @Override
    public void createInvite(PlayerRef sourcePlayer, PlayerRef targetPlayer) {
        this.invites.save(targetPlayer, sourcePlayer);
    }

    @Override
    public void acceptInvite(PlayerRef targetPlayer) {
        this.invites.find(targetPlayer).ifPresent(sender -> {
            Party party = getPartyForPlayer(sender).orElseGet(() -> {
                Party newParty = new Party(sender, new HashSet<>());
                newParty.addPlayer(sender);
                return newParty;
            });
            party.addPlayer(targetPlayer);
            store.save(sender, party);
            invites.delete(targetPlayer);
        });
    }

    @Override
    public boolean hasInvite(PlayerRef targetPlayer) {
        return this.invites.exists(targetPlayer);
    }

    @Override
    public boolean isInParty(PlayerRef playerRef) {
        return store.values().anyMatch(party -> party.hasPlayer(playerRef));
    }

    @Override
    public Optional<Party> getPartyForPlayer(PlayerRef playerRef) {
        Optional<Party> maybeParty = store.find(playerRef);
        // is party owner
        if (maybeParty.isPresent()) {
            return maybeParty;
        }
        // is in party
        return store.values().filter(party -> party.hasPlayer(playerRef))
                .findFirst();
    }

    @Override
    public Stream<PlayerRef> getPartyMembersFor(PlayerRef playerRef) {
        return store.values().filter(party -> party.hasPlayer(playerRef))
                .findFirst()
                .map(Party::getMembers)
                .orElseGet(Collections::emptyList)
                .stream();
        //                .filter(p -> !p.equals(playerRef));
    }

    @Override
    public void leave(PlayerRef playerRef) {
        // TODO notify all players the player has left
        store.values().filter(party -> party.hasPlayer(playerRef))
                .forEach(party -> {
                    party.removePlayer(playerRef);
                    // TODO remove party if empty.
                });
    }
}
