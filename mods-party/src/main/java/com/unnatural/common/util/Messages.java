package com.unnatural.common.util;

import com.google.common.collect.Sets;
import com.hypixel.hytale.server.core.Message;

import java.awt.Color;
import java.util.Set;

public final class Messages {

    private static final Set<String> TEST = Sets.newHashSet();

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
