package io.github.teampropulsive.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ShipDownKeybind {
    private static KeyBinding shipDownKeybind;
    public static void ShipDownKeybind() {
        shipDownKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.propulsive.ship.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_SEMICOLON,
                "category.propulsive.keys"
        ));

    }
}
