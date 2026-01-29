package com.unnatural.party.gui;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PartyHudPlayerData {
    private final UUID playerUuid;
    private boolean isLeader;
    private String username;
    private Integer healthPercent;
    private Direction direction;
}
