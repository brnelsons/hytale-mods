package com.unnatural.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.party.service.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class PartyCommandInvite extends AbstractPlayerCommand {

    private final PartyService partyService;
    private final RequiredArg<String> targetPlayerArg;

    public PartyCommandInvite(PartyService partyService) {
        super("invite", "invites someone to your party");
        this.partyService = partyService;
        this.targetPlayerArg = withRequiredArg("targetPlayer", "the username of the player", ArgTypes.STRING);
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext,
                           @NonNullDecl Store<EntityStore> store,
                           @NonNullDecl Ref<EntityStore> ref,
                           @NonNullDecl PlayerRef playerRef,
                           @NonNullDecl World world) {
        final String targetPlayer = this.targetPlayerArg.get(commandContext);
        partyService.createInvite(playerRef, targetPlayer);
    }
}
