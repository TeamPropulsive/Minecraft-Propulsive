package io.github.teampropulsive.keybind;

import io.github.teampropulsive.screen.MapScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ShipThrottleDownKeybind {
    private static KeyBinding shipThrottleDownKeybind;
    public static void ShipThrottleDownKeybind() {
        shipThrottleDownKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.propulsive.ship.tdown",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_SEMICOLON,
                "category.propulsive.keys"
        ));

    }
}
