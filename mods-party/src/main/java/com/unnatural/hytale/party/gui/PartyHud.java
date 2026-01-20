package com.unnatural.hytale.party.gui;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.unnatural.hytale.party.service.PartyService;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PartyHud extends CustomUIHud {
    private static final String DOCUMENT_REF = "PartyHud.ui";
    private static final String MEMBER_REF = "PartyMember.ui";

    private final PartyService partyService;

    public PartyHud(@NonNull PlayerRef playerRef,
                    @NonNull PartyService partyService) {
        super(playerRef);
        this.partyService = partyService;
    }

    @Override
    protected void build(@NonNull UICommandBuilder cmd) {
        cmd.append(DOCUMENT_REF);
        partyService.findParty(getPlayerRef()).ifPresent(party -> {
            List<PlayerRef> partyMembers = party.getMembers().stream()
                    // TODO should this get all players and access the map non-concurrently?
                    .map(playerUuid -> Universe.get().getPlayer(playerUuid))
                    .filter(Objects::nonNull)
                    .toList();
            setPartyMembers(cmd, party.getLeader(), partyMembers);
        });
    }

    @Override
    public void update(boolean clear, @NonNull UICommandBuilder cmd) {
        super.update(clear, cmd);
        partyService.findParty(getPlayerRef()).ifPresent(party -> {
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
