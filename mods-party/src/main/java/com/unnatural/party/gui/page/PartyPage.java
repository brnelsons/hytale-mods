package com.unnatural.party.gui.page;

import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.player.pages.BasicCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.unnatural.party.service.PartyService;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * TODO instead of a page we should create a custom HUD element for the party.
 * Note: This does not update in real time, the UI has to be closed and opened to get any updates.
 */
public class PartyPage extends BasicCustomUIPage {
    private static final String PAGE_REF = "Pages/PartyPage.ui";
    private static final String MEMBER_REF = "PartyMember.ui";

    private final PartyService partyService;

    public PartyPage(@NonNullDecl PlayerRef playerRef,
                     @NonNullDecl PartyService partyService) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction);
        this.partyService = partyService;
    }

    @Override
    public void build(UICommandBuilder cmd) {
        cmd.append(PAGE_REF);
        partyService.findParty(playerRef).ifPresent(party -> {
            List<PlayerRef> partyMembers = party.getMembers().stream()
                    // TODO should this get all players and access the map non-concurrently?
                    .map(playerUuid -> Universe.get().getPlayer(playerUuid))
                    .filter(Objects::nonNull)
                    .toList();
            setPartyMembers(cmd, party.getLeader(), partyMembers);
        });
    }


    private void setPartyMembers(UICommandBuilder cmd, UUID leaderUuid, List<PlayerRef> members) {
        cmd.clear("#PartyMembers");
        for (int i = 0; i < members.size(); i++) {
            cmd.append("#PartyMembers", MEMBER_REF);
            PlayerRef partyMember = members.get(i);
            String selector = String.format("#PartyMembers[%d] ", i);
            if (partyMember.getUuid().equals(leaderUuid)) {
                cmd.set(selector + "#Leader.Text", partyMember.getUsername());
                cmd.set(selector + "#Leader.Visible", true);
            } else {
                cmd.set(selector + "#Name.Text", partyMember.getUsername());
                cmd.set(selector + "#Name.Visible", true);
            }
        }
    }
}
