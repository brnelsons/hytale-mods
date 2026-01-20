package com.unnatural.hytale.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.hytale.party.hud.PartyHud;
import com.unnatural.hytale.party.service.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class PartyCommand extends AbstractPlayerCommand {

    private final PartyService partyService;

    public PartyCommand(PartyService partyService) {
        super("party", "Manages party");
        this.partyService = partyService;
        addSubCommand(new PartyCommandTest(partyService));
        addSubCommand(new PartyCommandInvite(partyService));
        addSubCommand(new PartyCommandLeave(partyService));
        addSubCommand(new PartyCommandAccept(partyService));
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext cmd,
                           @NonNullDecl Store<EntityStore> store,
                           @NonNullDecl Ref<EntityStore> ref,
                           @NonNullDecl PlayerRef playerRef,
                           @NonNullDecl World world) {
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player != null) {
            PartyHud hud = new PartyHud(playerRef, partyService);
            player.getHudManager().setCustomHud(playerRef, hud);
            //TODO update HUD when player is added/removed from the party.
        }
        //        PartyPage page = new PartyPage(playerRef, partyService);
        //        if (player != null) {
        //            player.getPageManager().openCustomPage(ref, store, page);
        //        }
    }
}
