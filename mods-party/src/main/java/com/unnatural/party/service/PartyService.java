package com.unnatural.party.service;

import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.unnatural.common.storage.InMemoryStore;
import com.unnatural.common.storage.Store;
import com.unnatural.common.util.Messages;
import com.unnatural.party.model.Party;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartyService {

    private final Store<UUID, Party> store;
    private final Store<UUID, UUID> invites;

    public PartyService(Store<UUID, Party> store) {
        this.store = store;
        this.invites = new InMemoryStore<>();
    }

    public void createParty(Party party) {
        store.save(party.getUuid(), party);
    }

    public void createInvite(PlayerRef sourcePlayerRef, String targetUsername) {
        if (targetUsername.equalsIgnoreCase(sourcePlayerRef.getUsername())) {
            sourcePlayerRef.sendMessage(Messages.warning("cannot create party with yourself"));
            return;
        }
        PlayerRef targetPlayerRef = Universe.get()
                .getPlayerByUsername(targetUsername, NameMatching.EXACT_IGNORE_CASE);
        if (targetPlayerRef == null) {
            sourcePlayerRef.sendMessage(Messages.warning("player not found: " + targetUsername));
        } else {
            // TODO target player has pending invite
            if (isInParty(targetPlayerRef)) {
                sourcePlayerRef.sendMessage(Messages.warning("player is in another party"));
            } else {
                this.invites.save(targetPlayerRef.getUuid(), sourcePlayerRef.getUuid());
                targetPlayerRef.sendMessage(Messages.important("Party invite from " + sourcePlayerRef));
                sourcePlayerRef.sendMessage(Messages.important("Sent invite to " + targetUsername));
            }
        }
    }

    public void acceptInvite(PlayerRef playerRef) {
        UUID playerUuid = playerRef.getUuid();
        this.invites.find(playerUuid)
                .ifPresentOrElse(senderUuid -> {
                    Party party = findParty(senderUuid).orElseGet(() -> {
                        Party newParty = Party.create(senderUuid);
                        newParty.addPlayer(senderUuid);
                        return newParty;
                    });
                    party.addPlayer(playerUuid);
                    store.save(senderUuid, party);
                    invites.delete(playerUuid);
                    getPartyMembersFor(playerRef.getUuid())
                            .map(memberUuid -> Universe.get().getPlayer(memberUuid))
                            .filter(Objects::nonNull)
                            .forEach(member ->
                                    member.sendMessage(Messages.important(playerRef.getUsername() + " has joined the party: ")));
                }, () -> playerRef.sendMessage(Messages.warning("no party invite available")));
    }

    public boolean isInParty(PlayerRef playerRef) {
        return isInParty(playerRef.getUuid());
    }

    public boolean isInParty(UUID playerUuid) {
        return store.values().anyMatch(party -> party.hasPlayer(playerUuid));
    }

    public Optional<Party> findParty(PlayerRef playerRef) {
        return findParty(playerRef.getUuid());
    }

    public Optional<Party> findParty(UUID playerUuid) {
        Optional<Party> maybeParty = store.find(playerUuid);
        // is party owner
        if (maybeParty.isPresent()) {
            return maybeParty;
        }
        // is in party
        return store.values().filter(party -> party.hasPlayer(playerUuid))
                .findFirst();
    }

    public Optional<Party> getPartyFor(UUID playerUuid) {
        return store.values().filter(party -> party.hasPlayer(playerUuid))
                .findFirst();
    }

    public Stream<UUID> getPartyMembersFor(PlayerRef playerRef) {
        return getPartyMembersFor(playerRef.getUuid());
    }

    public Stream<UUID> getPartyMembersFor(UUID playerUuid) {
        return getPartyFor(playerUuid)
                .map(Party::getMembers)
                .orElseGet(Collections::emptyList)
                .stream();
        //                .filter(p -> !p.equals(playerRef));
    }

    public void leave(PlayerRef playerRef) {
        findParty(playerRef).ifPresentOrElse(
                party -> {
                    Set<PlayerRef> members = party.getMembers()
                            .stream()
                            .map(playerUuid -> Universe.get().getPlayer(playerUuid))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
                    party.removePlayer(playerRef.getUuid());
                    playerRef.sendMessage(Messages.important("left party"));
                    members.forEach(member -> member.sendMessage(Messages.important(playerRef.getUsername() + " has left the party")));
                },
                () -> playerRef.sendMessage(Messages.warning("not in a party"))
        );
    }
}
