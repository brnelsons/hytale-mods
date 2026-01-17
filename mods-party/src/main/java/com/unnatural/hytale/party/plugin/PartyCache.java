package com.unnatural.hytale.party.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartyCache {
    private final Map<UUID, Party> cache = new HashMap<>();

    public void add(Party party) {
        cache.put(party.getUuid(), party);
    }
}
