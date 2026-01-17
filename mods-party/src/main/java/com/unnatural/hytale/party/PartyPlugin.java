package com.unnatural.hytale.party;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.unnatural.hytale.party.dep.PartyCommand;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

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
        getCommandRegistry().registerCommand(new PartyCommand());
    }
}
