package com.unnatural.hytale.party.cmd;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.unnatural.hytale.party.plugin.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.Color;

public class PartyCommand extends CommandBase {

    public PartyCommand(PartyService partyService) {
        super("party", "Manages party");
        addSubCommand(new PartyCommandInvite(partyService));
        addSubCommand(new PartyCommandLeave(partyService));
        addSubCommand(new PartyCommandAccept(partyService));
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        // TODO print party info if already in a party.
        commandContext.sendMessage(Message.raw("-- Party").color(Color.YELLOW));
        // TODO maybe move this to --help command?
        //        commandContext.sendMessage(createCmdMessage("/party", "create", "Create a new party"));
        //        commandContext.sendMessage(createCmdMessage("/party", "disband", "Disbands an existing party"));
        //        commandContext.sendMessage(createCmdMessage("/party", "invite", "Invites a player to your party", "playerName"));
    }

    //    private Message createCmdMessage(String parent,
    //                                     String name,
    //                                     String description,
    //                                     String... variables) {
    //        List<Message> messages = new ArrayList<>(4);
    //        messages.add(Message.raw(parent).color(Color.WHITE));
    //        messages.add(Message.raw(name).color(Color.WHITE));
    //        for (String variable : variables) {
    //            messages.add(Message.raw(String.format("{%s}", variable)).color(Color.GREEN));
    //        }
    //        messages.add(Message.raw(String.format(" - %s", description)).color(Color.WHITE));
    //        return Message.join(messages.toArray(new Message[]{}));
    //    }
}
