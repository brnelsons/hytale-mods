package com.unnatural.hytale.party;

import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.events.AddWorldEvent;
import com.unnatural.hytale.common.storage.InMemoryStore;
import com.unnatural.hytale.party.cmd.PartyCommand;
import com.unnatural.hytale.party.gui.PartyCompassMarkerProvider;
import com.unnatural.hytale.party.service.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

@SuppressWarnings("unused")
public class PartyPlugin extends JavaPlugin {

    private static PartyPlugin INSTANCE;

    public PartyPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
        INSTANCE = this;
    }

    public static PartyPlugin getInstance() {
        return INSTANCE;
    }

    @Override
    protected void start0() {
        //        super.start0();
        PartyService partyService = new PartyService(InMemoryStore.create());
        getCommandRegistry().registerCommand(new PartyCommand(partyService));
        getEventRegistry().register(PlayerDisconnectEvent.class, playerDisconnectEvent -> {
            partyService.leave(playerDisconnectEvent.getPlayerRef());
        });
        getEventRegistry().registerGlobal(AddWorldEvent.class, event -> onAddWorld(event, partyService));
    }

    private void onAddWorld(AddWorldEvent event,
                            PartyService partyService) {
        event.getWorld().getWorldMapManager().addMarkerProvider("party", new PartyCompassMarkerProvider(partyService));
    }


}
