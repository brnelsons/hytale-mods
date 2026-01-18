package com.unnatural.hytale.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.hytale.common.util.Messages;
import com.unnatural.hytale.party.plugin.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

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

        return CompletableFuture.runAsync(() -> {
            final String sourcePlayer = playerRef.getUsername();
            final String targetPlayer = this.targetPlayerArg.get(commandContext);
            //            if (targetPlayer.equalsIgnoreCase(sourcePlayer)) {
            //                playerRef.sendMessage(Message.raw("cannot create party with yourself").color(Color.ORANGE));
            //                return;
            //            }
            world.getPlayerRefs()
                    .stream()
                    .filter(player -> player.getUsername().equalsIgnoreCase(targetPlayer))
                    .findFirst()
                    .ifPresentOrElse(target -> {
                        // TODO target player has pending invite
                        if (partyService.isInParty(target)) {
                            target.sendMessage(Messages.warning("player is in another party"));
                        } else {
                            partyService.createInvite(playerRef, target);
                            target.sendMessage(Messages.important("Party invite from " + sourcePlayer));
                            playerRef.sendMessage(Messages.important("Sent invite to " + targetPlayer));
                        }
                    }, () -> playerRef.sendMessage(Messages.error("player not found: " + targetPlayer)));
        }, world);
    }
}
