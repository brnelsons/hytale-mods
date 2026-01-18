package com.unnatural.hytale.common.util;

import com.hypixel.hytale.server.core.Message;

import java.awt.Color;

public final class Messages {

    private Messages() {
        // no-op
    }

    public static Message good(String text) {
        return Message.raw(text).color(Color.GREEN);
    }

    public static Message important(String text) {
        return Message.raw(text).color(Color.PINK);
    }

    public static Message warning(String text) {
        return Message.raw(text).color(Color.ORANGE);
    }

    public static Message error(String text) {
        return Message.raw(text).color(Color.RED);
    }

}
