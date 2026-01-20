package com.unnatural.hytale.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.hytale.party.model.Party;
import com.unnatural.hytale.party.service.PartyService;
import org.jspecify.annotations.NonNull;

public class PartyCommandTest extends AbstractPlayerCommand {

    private final PartyService partyService;

    public PartyCommandTest(PartyService partyService) {
        super("test", "creates a test party");
        this.partyService = partyService;
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@NonNull CommandContext var1,
                           @NonNull Store<EntityStore> var2,
                           @NonNull Ref<EntityStore> var3,
                           @NonNull PlayerRef playerRef,
                           @NonNull World var5) {
        partyService.createParty(Party.create(playerRef));
    }
}
