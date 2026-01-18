package com.unnatural.hytale.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.hytale.common.util.Messages;
import com.unnatural.hytale.party.plugin.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PartyCommandLeave extends AbstractAsyncPlayerCommand {

    private final PartyService partyService;

    public PartyCommandLeave(PartyService partyService) {
        super("leave", "leaves the current party");
        this.partyService = partyService;
    }

    @NonNullDecl
    @Override
    protected CompletableFuture<Void> executeAsync(@NonNullDecl CommandContext commandContext,
                                                   @NonNullDecl Store<EntityStore> store,
                                                   @NonNullDecl Ref<EntityStore> ref,
                                                   @NonNullDecl PlayerRef playerRef,
                                                   @NonNullDecl World world) {
        return CompletableFuture.runAsync(() -> {
            if (partyService.isInParty(playerRef)) {
                Set<UUID> players = partyService.getPlayersInPartyWith(playerRef)
                        .filter(p -> p.equals(playerRef.getUuid()))
                        .collect(Collectors.toSet());
                partyService.leave(playerRef);
                commandContext.sendMessage(Messages.important("left party"));
                world.getPlayerRefs()
                        .stream()
                        .filter(p -> players.contains(p.getUuid()))
                        .forEach(otherPlayer -> otherPlayer.sendMessage(Messages.important(playerRef.getUsername() + " has left the party")));
            }
        }, world);
    }
}
