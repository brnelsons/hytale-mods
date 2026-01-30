package com.unnatural.party.event;

import com.hypixel.hytale.event.IAsyncEvent;
import lombok.Data;

@Data
public class PartyMemberHealthChangeEvent implements IAsyncEvent<Double> {
    private Double currentHealthPct;
}
