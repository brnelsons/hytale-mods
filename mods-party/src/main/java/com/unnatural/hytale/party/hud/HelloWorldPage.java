package com.unnatural.hytale.party.hud;

import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.entity.entities.player.pages.BasicCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.unnatural.codegen.CustomTemplate;
import com.unnatural.codegen.CustomTemplateType;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

@CustomTemplate(
        type = CustomTemplateType.PAGE,
        // language=UI
        value = """
                Group {
                  Anchor: (Width: 400, Height: 250);
                  Background: #1a1a2e(0.95);
                  LayoutMode: Top;
                  Padding: (Full: 20);
                  Label #Title {
                    Text: "Hello World";
                    Anchor: (Height: 40);
                    Style: (FontSize: 24, TextColor: #ffffff, Alignment: Center);
                  }
                }
                """)
public class HelloWorldPage extends BasicCustomUIPage {

    public HelloWorldPage(@NonNullDecl PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss);
    }

    @Override
    public void build(UICommandBuilder cmd) {
        cmd.append("Pages/HelloWorldPage2.ui");
    }
}
