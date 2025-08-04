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
    @Override
    public void onInitializeClient() {
        // Rendering
        DimensionRenderingRegistry.registerSkyRenderer(RegistryKey.of(RegistryKeys.WORLD, Propulsive.id("space")), new SpaceSkyRenderer());
        DimensionRenderingRegistry.registerDimensionEffects(Propulsive.id("space"), new SpaceDimensionEffects());
    }
}
