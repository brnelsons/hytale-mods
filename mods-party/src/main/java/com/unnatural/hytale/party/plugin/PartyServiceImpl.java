package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.unnatural.hytale.common.storage.InMemoryStore;
import com.unnatural.hytale.common.storage.Store;
import com.unnatural.hytale.party.model.Party;

import java.util.UUID;
import java.util.stream.Stream;

public class PartyServiceImpl implements PartyService {

    private final Store<UUID, Party> store;
    private final Store<UUID, UUID> invites;

    public PartyServiceImpl(Store<UUID, Party> store) {
        this.store = store;
        this.invites = new InMemoryStore<>();
    }

    @Override
    public void createInvite(PlayerRef sourcePlayer, PlayerRef targetPlayer) {
        this.invites.save(targetPlayer.getUuid(), sourcePlayer.getUuid());
    }

    @Override
    public void acceptInvite(PlayerRef targetPlayer) {
        this.invites.find(targetPlayer.getUuid()).ifPresent(inviteSenderUuid -> {
            Party newParty = new Party(inviteSenderUuid);
            newParty.addPlayer(inviteSenderUuid);
            newParty.addPlayer(targetPlayer.getUuid());
            store.save(newParty.getLeaderUuid(), newParty);
            invites.delete(targetPlayer.getUuid());
        });
    }

    @Override
    public boolean hasInvite(PlayerRef targetPlayer) {
        return this.invites.exists(targetPlayer.getUuid());
    }

    @Override
    public boolean isInParty(PlayerRef playerRef) {
        return store.values().anyMatch(party -> party.hasPlayer(playerRef.getUuid()));
    }

    @Override
    public Stream<UUID> getPlayersInPartyWith(PlayerRef playerRef) {
        return store.values().filter(party -> party.hasPlayer(playerRef.getUuid()))
                .findFirst()
                .map(Party::getPlayerUuids).orElse(Stream.of());
    }

    @Override
    public void leave(PlayerRef playerRef) {
        // TODO notify all players the player has left
        store.values().filter(party -> party.hasPlayer(playerRef.getUuid()))
                .forEach(party -> {
                    party.removePlayer(playerRef.getUuid());
                    // TODO remove party if empty.
                });
    }
}
