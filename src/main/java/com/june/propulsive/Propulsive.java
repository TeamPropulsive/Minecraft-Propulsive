package com.june.propulsive;

import com.june.propulsive.types.Planet;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;


public class Propulsive implements ModInitializer {
    @Override
    public void onInitialize() {
        EARTH.render();
        MOON.render();
    }

    // Dimensions
    public static RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, new Identifier("propulsive:space"));

    // Planet configuration
    // EARTH
    public static final double OVERWORLD_SPACE_POSX = 0.0;
    public static final double OVERWORLD_SPACE_POSY = 0.0;
    public static final double OVERWORLD_SPACE_POSZ = 0.0;
    public static final float OVERWORLD_SPACE_SIZE = 1.0f;
    public static final double OVERWORLD_HEIGHT = 128.0;

    // MOON (NYI, only renders for now)
    public static final double MOON_SPACE_POSX = 0.0;
    public static final double MOON_SPACE_POSY = 6.0;
    public static final double MOON_SPACE_POSZ = 0.0;
    public static final float MOON_SPACE_SIZE = 0.5f;
    public static final double MOON_HEIGHT = 128.0;

    // Actually creating the planets
    public static Planet EARTH = new Planet(OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_POSX, OVERWORLD_SPACE_POSY, OVERWORLD_SPACE_POSZ, new Identifier("propulsive:textures/planets/earth.png"));
    public static Planet MOON = new Planet(MOON_SPACE_SIZE, MOON_SPACE_POSX, MOON_SPACE_POSY, MOON_SPACE_POSZ, new Identifier("propulsive:textures/planets/moon.png"));
}
