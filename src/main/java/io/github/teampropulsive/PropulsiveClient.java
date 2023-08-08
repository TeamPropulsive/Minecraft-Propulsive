package io.github.teampropulsive;

import io.github.teampropulsive.client.world.SpaceDimensionEffects;
import io.github.teampropulsive.client.world.SpaceSkyRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static io.github.teampropulsive.Propulsive.*;
import static io.github.teampropulsive.keybind.MapScreenKeybind.MapScreenKeybindRegister;

@Environment(EnvType.CLIENT)
public class PropulsiveClient implements ClientModInitializer {
    public static List<String[]> capes = new ArrayList<>();
    @Override
    public void onInitializeClient() {
        // Capes
        capes.add(new String[] { "9e784cc7-753d-4655-89be-c196eed1a274", "primavera" });
        capes.add(new String[] { "56db0044-9fc8-4ae3-83d6-1adee75aa799", "jamiscus" });
        capes.add(new String[] { "cd285b68-5039-412b-8ef1-35470223221d", "potato" });
        capes.add(new String[] { "b641da4d-908f-4848-8576-3bbe56ab2efa", "haskeller" });
        capes.addAll(getContributors("https://raw.githubusercontent.com/Team-Propulsive/Minecraft-Propulsive/main/contributors.txt"));
        // Rendering
        DimensionRenderingRegistry.registerSkyRenderer(RegistryKey.of(RegistryKeys.WORLD, Propulsive.id("space")), new SpaceSkyRenderer());
        DimensionRenderingRegistry.registerDimensionEffects(Propulsive.id("space"), new SpaceDimensionEffects());
        MOON.render();
        SUN.render();
        EARTH.render();
        // Key binds
        MapScreenKeybindRegister();
    }

    // Returns contributors from git repo
    public static List<String[]> getContributors(String fileUrl) {
        List<String[]> contentWithContributor = new ArrayList<>();
        try {
            URL url = new URL(fileUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) contentWithContributor.add(new String[]{line, "contributor"});
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contentWithContributor;
    }
}
