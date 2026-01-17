package com.unnatural.hytale.party.plugin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class PartyCommands {
    static final Message CREATE_PARTY_MSG = Message.raw("Created party").color(Color.GREEN);
    public static class Create extends AbstractAsyncPlayerCommand {

        private final PartyCache partyCache;

        public Create(PartyCache partyCache) {
            super("create", "creates a new party");
            this.partyCache = partyCache;
        }

        @NonNullDecl
        @Override
        protected CompletableFuture<Void> executeAsync(@NonNullDecl CommandContext commandContext,
                                                       @NonNullDecl Store<EntityStore> store,
                                                       @NonNullDecl Ref<EntityStore> ref,
                                                       @NonNullDecl PlayerRef playerRef,
                                                       @NonNullDecl World world) {
            world.execute(() -> {
                Party party = new Party(UUID.randomUUID());
                party.setOwner(playerRef);
                party.addPlayer(playerRef);
                commandContext.sendMessage(CREATE_PARTY_MSG);
            });
            return CompletableFuture.completedFuture(null);
        }
    }
}
