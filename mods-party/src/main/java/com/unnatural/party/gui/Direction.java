package com.unnatural.party.gui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Direction {
    NORTH("^"),
    NORTH_EAST("^"),
    EAST(">"),
    SOUTH_EAST(">"),
    SOUTH("!"),
    SOUTH_WEST("!"),
    WEST("<"),
    NORTH_WEST("<"),
    ;

    private final String character;
}
