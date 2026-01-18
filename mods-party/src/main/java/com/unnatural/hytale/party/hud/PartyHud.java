package com.unnatural.hytale.party.hud;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.concurrent.atomic.AtomicInteger;

public class PartyHud extends CustomUIHud {
    private static final AtomicInteger tick = new AtomicInteger(0);

    public PartyHud(@NonNullDecl PlayerRef playerRef) {
        super(playerRef);
    }

    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append( "Party/test.ui")
                .set("#TestLabel.TextSpans", Message.raw("" + tick.incrementAndGet()));
    }
}
