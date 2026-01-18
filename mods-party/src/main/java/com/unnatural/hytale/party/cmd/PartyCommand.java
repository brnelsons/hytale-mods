package com.unnatural.hytale.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.hytale.party.plugin.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.Color;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PartyCommand extends AbstractAsyncPlayerCommand {

    private final PartyService partyService;

    public PartyCommand(PartyService partyService) {
        super("party", "Manages party");
        this.partyService = partyService;
        addSubCommand(new PartyCommandInvite(partyService));
        addSubCommand(new PartyCommandLeave(partyService));
        addSubCommand(new PartyCommandAccept(partyService));
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @NonNullDecl
    @Override
    protected CompletableFuture<Void> executeAsync(@NonNullDecl CommandContext commandContext,
                                                   @NonNullDecl Store<EntityStore> store,
                                                   @NonNullDecl Ref<EntityStore> ref,
                                                   @NonNullDecl PlayerRef playerRef,
                                                   @NonNullDecl World world) {
        return CompletableFuture.runAsync(() -> {
            playerRef.sendMessage(Message.raw("-- Party").color(Color.YELLOW));
            if (partyService.isInParty(playerRef)) {
                Set<UUID> playersInParty = partyService.getPlayersInPartyWith(playerRef).collect(Collectors.toSet());
                world.getPlayerRefs().stream().filter(p -> playersInParty.contains(p.getUuid()))
                        .forEach(player -> playerRef.sendMessage(Message.raw("\t*\t" + player.getUsername())));
            }
        });
    }
}
