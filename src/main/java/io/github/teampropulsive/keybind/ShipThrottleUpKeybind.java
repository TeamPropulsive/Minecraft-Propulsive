package io.github.teampropulsive.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ShipThrottleUpKeybind {
    public static KeyBinding shipThrottleUpKeybind;
    public static void shipThrottleUpKeybind() {
        shipThrottleUpKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.propulsive.ship.tup",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_BACKSLASH,
                "category.propulsive.keys"
        ));

    }
}
