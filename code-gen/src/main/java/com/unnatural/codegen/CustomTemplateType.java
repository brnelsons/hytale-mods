package com.unnatural.codegen;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomTemplateType {
    PAGE("Pages", "ui"),
    HUD("Hud", "ui"),
    ;

    private final String baseLocation;
    private final String extension;
}
