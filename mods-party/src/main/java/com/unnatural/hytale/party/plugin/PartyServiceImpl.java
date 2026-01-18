package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.unnatural.hytale.common.storage.Store;
import com.unnatural.hytale.party.model.Party;

import java.util.UUID;

public class PartyServiceImpl implements PartyService {

    private final Store<UUID, Party> store;

    public PartyServiceImpl(Store<UUID, Party> store) {
        this.store = store;
    }

    @Override
    public Party create(PlayerRef playerRef) {
        // TODO player isn't already associated with a party
        Party newParty = new Party(playerRef.getUuid());
        // TODO should we store player UUID or the playerRef?
        newParty.addPlayer(playerRef);
        store.save(playerRef.getUuid(), newParty);
        return newParty;
    }

    @Override
    public void invite(PlayerRef playerRef, String playerName) {
        // TODO find user and message them to join the party.
        // Keep track of party invites.
    }

    @Override
    public boolean isInParty(PlayerRef playerRef) {
        return store.values().anyMatch(party -> party.hasPlayer(playerRef));
    }

    @Override
    public void join(PlayerRef playerRef, String playerName) {
        // TODO check if invite exists
        // join party and remove invite
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
