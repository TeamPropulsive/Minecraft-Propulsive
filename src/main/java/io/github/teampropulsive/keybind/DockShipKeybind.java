package io.github.teampropulsive.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class DockShipKeybind {
    public static KeyBinding dockShipKey;
    public static void DockKeybindRegister() {
        dockShipKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.propulsive.dock",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.propulsive.keys"
        ));
    }
}
