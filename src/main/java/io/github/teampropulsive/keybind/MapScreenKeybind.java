package io.github.teampropulsive.keybind;

import io.github.teampropulsive.screen.MapScreen;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
public class MapScreenKeybind {
    private static KeyBinding openMapScreenKey;
    public static void mapScreenKeybindRegister() {
        openMapScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.propulsive.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_SEMICOLON,
                "category.propulsive.keys"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMapScreenKey.wasPressed()) {
                assert client.player != null;
                client.setScreen(new MapScreen());
            }
        });
    }
}
