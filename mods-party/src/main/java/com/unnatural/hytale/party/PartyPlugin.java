package com.unnatural.hytale.party;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.unnatural.hytale.party.plugin.PartyCache;
import com.unnatural.hytale.party.plugin.PartyCommand;
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
        PartyCache cache = new PartyCache();
        getCommandRegistry().registerCommand(new PartyCommand(cache));
    }
}
