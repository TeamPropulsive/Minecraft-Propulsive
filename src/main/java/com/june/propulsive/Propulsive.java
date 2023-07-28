package com.june.propulsive;

import com.june.propulsive.types.Planet;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.tick.Tick;

import java.util.*;

import static com.june.propulsive.keybind.MapScreenKeybind.MapScreenKeybindRegister;


public class Propulsive implements ModInitializer {
    public static ArrayList<Planet> TickablePlanets = new ArrayList<>();

    @Override
    public void onInitialize() {
        EARTH.render();
        MOON.render();
        MapScreenKeybindRegister();
        TickablePlanets.add(MOON);
        TickablePlanets.add(EARTH);
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

    // Misc config
    public static final double PLANET_3D_RENDER_DIST = 1000.0; // Distance at which planets go from 3D to 2D
    // Actually creating the planets
    public static Planet EARTH = new Planet(OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_POSX, OVERWORLD_SPACE_POSY, OVERWORLD_SPACE_POSZ, 16.0f, -20.0f, new Identifier("propulsive:textures/planets/earth.png"),  new Identifier("propulsive:textures/planets/earth_icon.png"));
    public static Planet MOON = new Planet(MOON_SPACE_SIZE, MOON_SPACE_POSX, MOON_SPACE_POSY, MOON_SPACE_POSZ, 45.0f, 25.0f, new Identifier("propulsive:textures/planets/moon.png"),  new Identifier("propulsive:textures/planets/moon_icon.png"));

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
