package com.unnatural.hytale.party.gui;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PartyHudPlayerData {
    private final UUID playerUuid;
    private boolean isLeader;
    private String username;
    private float healthPercent;
    private Direction direction;
}
