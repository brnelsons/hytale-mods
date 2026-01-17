package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class PartyCommand extends CommandBase {
    public PartyCommand(PartyCache partyCache) {
        super("party", "Manages party");
        addSubCommand(new PartyCommands.Create(partyCache));
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        commandContext.sendMessage(Message.raw("-- Party").color(java.awt.Color.YELLOW));
        commandContext.sendMessage(Message.raw("/party create - Create a new party").color(java.awt.Color.WHITE));
    }
}
