package com.unnatural.hytale.party.hud;

import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.player.pages.BasicCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.unnatural.hytale.party.plugin.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class PartyPage extends BasicCustomUIPage {

    private final PartyService partyService;

    public PartyPage(@NonNullDecl PlayerRef playerRef,
                     @NonNullDecl PartyService partyService) {
        super(playerRef, CustomPageLifetime.CanDismiss);
        this.partyService = partyService;
    }

    @Override
    public void build(UICommandBuilder cmd) {
        cmd.append("Pages/PartyPage.ui");
    }
}
