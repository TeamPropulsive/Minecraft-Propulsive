package com.june.propulsive;

import com.june.propulsive.render.EarthRenderer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;


public class Propulsive implements ModInitializer {
    @Override
    public void onInitialize() {
        EarthRenderer.render();
    }

    // Might make these configurable later
    public static final double OVERWORLD_SPACE_POSX = 0.0;
    public static final double OVERWORLD_SPACE_POSY = 0.0;
    public static final double OVERWORLD_SPACE_POSZ = 0.0;
    public static final float OVERWORLD_SPACE_SIZE = 1.0f;
    public static final double OVERWORLD_FACE_SIZE = 16.0;
    public static final double OVERWORLD_HEIGHT = 128.0;

    // Dimensions
    public static RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, new Identifier("propulsive:space"));
    public static Identifier BLANK_TEX = new Identifier("textures/blank.png");
}
