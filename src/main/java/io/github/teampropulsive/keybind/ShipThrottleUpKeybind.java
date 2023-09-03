package io.github.teampropulsive.keybind;

import io.github.teampropulsive.screen.MapScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ShipThrottleUpKeybind {
    private static KeyBinding shipThrottleUpKeybind;
    public static void ShipThrottleUpKeybind() {
        shipThrottleUpKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.propulsive.ship.tup",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_BACKSLASH,
                "category.propulsive.keys"
        ));

    }
}
