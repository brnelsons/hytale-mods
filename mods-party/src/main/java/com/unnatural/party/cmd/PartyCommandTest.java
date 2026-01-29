package com.unnatural.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.party.gui.PartyHud;
import com.unnatural.party.model.Party;
import com.unnatural.party.service.PartyService;
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
    protected void execute(@NonNull CommandContext cmd,
                           @NonNull Store<EntityStore> store,
                           @NonNull Ref<EntityStore> ref,
                           @NonNull PlayerRef playerRef,
                           @NonNull World world) {
        partyService.createParty(Party.create(playerRef));
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player != null) {
            PartyHud hud = new PartyHud(playerRef, partyService);
            player.getHudManager().setCustomHud(playerRef, hud);
            //TODO update HUD when player is added/removed from the party.
        }
    }
}
