package com.unnatural.hytale.party.dep;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class PartyCommand extends CommandBase {
    public PartyCommand() {
        super("party", "Manages party");
    }

    @Override
    protected boolean canGeneratePermission() {
        // Disable permission generation - allow all players to use
        return false;
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        commandContext.sendMessage(Message.raw("-- Party").color(java.awt.Color.YELLOW));
    }
}
