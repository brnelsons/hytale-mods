package com.unnatural.hytale.party.hud;

import com.unnatural.codegen.CustomTemplate;

@CustomTemplate(
        target = "example.ui",
        // language=UI
        value = """
                Group #MyGroup {
                    Anchor: (Width: 400, Height: 250);
                    Backgrounmd: #1a1a2e(0.95);
                }
                """
)
public class TestElement {
    void exampleMethod() {
        //
    }
}
