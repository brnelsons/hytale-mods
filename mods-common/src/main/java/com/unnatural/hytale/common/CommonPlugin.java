package com.unnatural.hytale.common;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class CommonPlugin extends JavaPlugin {

    private static CommonPlugin INSTANCE;

    public CommonPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
        INSTANCE = this;
    }
}
