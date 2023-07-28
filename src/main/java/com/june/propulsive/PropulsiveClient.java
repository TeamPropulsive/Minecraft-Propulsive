package com.june.propulsive;

import com.june.propulsive.client.world.SpaceDimensionEffects;
import com.june.propulsive.client.world.SpaceSkyRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

@Environment(EnvType.CLIENT)
public class PropulsiveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DimensionRenderingRegistry.registerSkyRenderer(RegistryKey.of(RegistryKeys.WORLD, Propulsive.id("space")), new SpaceSkyRenderer());
        DimensionRenderingRegistry.registerDimensionEffects(Propulsive.id("space"), new SpaceDimensionEffects());
    }
}
