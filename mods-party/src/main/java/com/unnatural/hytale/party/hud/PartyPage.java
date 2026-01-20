package com.unnatural.hytale.party.hud;

import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.player.pages.BasicCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.unnatural.hytale.party.model.Party;
import com.unnatural.hytale.party.plugin.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.List;
import java.util.Optional;

public class PartyPage extends BasicCustomUIPage {

    private final PartyService partyService;

    public PartyPage(@NonNullDecl PlayerRef playerRef,
                     @NonNullDecl PartyService partyService) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction);
        this.partyService = partyService;
    }

    @Override
    public void build(UICommandBuilder cmd) {
        cmd.append("Pages/PartyPage.ui");
        Optional<Party> maybeParty = partyService.getPartyForPlayer(playerRef);
        if (maybeParty.isPresent()) {
            Party party = maybeParty.get();
            List<PlayerRef> partyMembers = party.getMembers();
            for (int i = 0; i < partyMembers.size(); i++) {
                cmd.append("#PartyMembers", "Pages/PartyMember.ui");
                PlayerRef partyMember = partyMembers.get(i);
                if (partyMember.equals(party.getLeader())) {
                    cmd.set(String.format("#PartyMembers[%d] #Leader.Text", i), partyMember.getUsername());
                    cmd.set(String.format("#PartyMembers[%d] #Leader.Visible", i), true);
                } else {
                    cmd.set(String.format("#PartyMembers[%d] #Name.Text", i), partyMember.getUsername());
                    cmd.set(String.format("#PartyMembers[%d] #Name.Visible", i), true);
                }
            }
        }
    }
}
