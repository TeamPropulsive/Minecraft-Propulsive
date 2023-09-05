package io.github.teampropulsive.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ShipThrottleDownKeybind {
    public static KeyBinding shipThrottleDownKeybind;
    public static void shipThrottleDownKeybind() {
        shipThrottleDownKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.propulsive.ship.tdown",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_CONTROL,
                "category.propulsive.keys"
        ));

    }
}
