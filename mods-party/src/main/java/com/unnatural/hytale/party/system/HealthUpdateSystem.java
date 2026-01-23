package com.unnatural.hytale.party.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class HealthUpdateSystem extends EntityTickingSystem<EntityStore> {

    @Override
    public void tick(float var1,
                     int var2,
                     @NonNull ArchetypeChunk<EntityStore> var3,
                     @NonNull Store<EntityStore> var4,
                     @NonNull CommandBuffer<EntityStore> var5) {

    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return null;
    }
}
