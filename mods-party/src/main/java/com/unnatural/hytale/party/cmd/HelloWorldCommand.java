package com.unnatural.hytale.party.cmd;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.unnatural.hytale.party.hud.HelloWorldPage;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class HelloWorldCommand extends AbstractPlayerCommand {
    public HelloWorldCommand() {
        super("helloworld", "opens a test dialog");
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
        HelloWorldPage page = new HelloWorldPage(playerRef);
        if (player != null) {
            player.getPageManager().openCustomPage(ref, store, page);
        }
    }
}
