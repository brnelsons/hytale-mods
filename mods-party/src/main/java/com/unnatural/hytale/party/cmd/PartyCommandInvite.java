package com.unnatural.hytale.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.hytale.party.plugin.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.Color;
import java.util.concurrent.CompletableFuture;

public class PartyCommandInvite extends AbstractAsyncPlayerCommand {

    private final PartyService partyService;
    private final RequiredArg<String> targetPlayerArg;

    public PartyCommandInvite(PartyService partyService) {
        super("invite", "invites someone to your party");
        this.partyService = partyService;
        this.targetPlayerArg = withRequiredArg("targetPlayer", "the username of the player", ArgTypes.STRING);
    }

    @NonNullDecl
    @Override
    protected CompletableFuture<Void> executeAsync(@NonNullDecl CommandContext commandContext,
                                                   @NonNullDecl Store<EntityStore> store,
                                                   @NonNullDecl Ref<EntityStore> ref,
                                                   @NonNullDecl PlayerRef playerRef,
                                                   @NonNullDecl World world) {
        // TODO should all of this be inside the completable future?
        String targetPlayer = this.targetPlayerArg.get(commandContext);
        return CompletableFuture.runAsync(() -> world.getPlayerRefs()
                .stream()
                .filter(allPlayers -> allPlayers.getUsername().equalsIgnoreCase(targetPlayer))
                .findFirst()
                .ifPresentOrElse(target -> {
                    if (target.equals(playerRef)) {
                        commandContext.sendMessage(Message.raw("cannot create party with yourself").color(Color.ORANGE));
                    } else {
                        partyService.create(playerRef);
                        commandContext.sendMessage(Message.raw("Created party").color(Color.GREEN));
                    }
                }, () -> commandContext.sendMessage(Message.raw(String.format("player \"%s\" not found", targetPlayer)).color(Color.ORANGE))), world);
    }
}
