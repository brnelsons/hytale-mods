package com.unnatural.hytale.party;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.unnatural.hytale.common.storage.InMemoryStore;
import com.unnatural.hytale.party.cmd.PartyCommand;
import com.unnatural.hytale.party.plugin.PartyService;
import com.unnatural.hytale.party.plugin.PartyServiceImpl;
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
        PartyService partyService = new PartyServiceImpl(InMemoryStore.create());
        getCommandRegistry().registerCommand(new PartyCommand(partyService));
        // TODO register when a player disconnects or connects to manage the party. otherwise there will be memory leaks.
    }


}
