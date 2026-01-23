package com.unnatural.hytale.party;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.events.AddWorldEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.hytale.common.storage.InMemoryStore;
import com.unnatural.hytale.party.cmd.PartyCommand;
import com.unnatural.hytale.party.gui.PartyCompassMarkerProvider;
import com.unnatural.hytale.party.service.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public class PartyPlugin extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

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
        //        getEventRegistry().registerGlobal(PlayerReadyEvent.class, event -> onPlayerReady(event));
    }

    private void onAddWorld(AddWorldEvent event,
                            PartyService partyService) {
        LOGGER.atInfo().log("creating marker provider");
        event.getWorld().getWorldMapManager().addMarkerProvider("party", new PartyCompassMarkerProvider(partyService));
    }

    private void onPlayerReady(@NonNull PlayerReadyEvent event) {
        Ref<EntityStore> ref = event.getPlayerRef();
        PlayerRef playerRef = ref.getStore().getComponent(ref, PlayerRef.getComponentType());
        EntityStatMap entityStatMap = ref.getStore().getComponent(ref, EntityStatMap.getComponentType());
        EntityStatValue entityStatValue = entityStatMap.get(DefaultEntityStatTypes.getHealth());
    }


}
