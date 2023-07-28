package com.june.propulsive;

import com.june.propulsive.types.Planet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
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

        if (FabricLoader.getInstance().isModLoaded("ad_astra")) {
            // Ad Astra compatibility
        }
        if (FabricLoader.getInstance().isModLoaded("galacticraft")) {
            // GC5 Compatibility
        }

        MERCURY.render();
        VENUS.render();
        EARTH.render();
        MOON.render();
        MARS.render();

        MapScreenKeybindRegister();

        TickablePlanets.add(MERCURY);
        TickablePlanets.add(VENUS);
        TickablePlanets.add(EARTH);
        TickablePlanets.add(MOON);
        TickablePlanets.add(MARS);
    }

    // Dimensions
    public static RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, new Identifier("propulsive:space"));

    // Actually creating the planets
    // Mercury
    public static final double MERCURY_SPACE_POSX = 0.0;
    public static final double MERCURY_SPACE_POSY = 0.0;
    public static final double MERCURY_SPACE_POSZ = 0.0;
    public static final float MERCURY_SPACE_SIZE = 0.5f;
    public static final double MERCURY_HEIGHT = 128.0;
    // Venus
    public static final double VENUS_SPACE_POSX = 0.0;
    public static final double VENUS_SPACE_POSY = 6.0;
    public static final double VENUS_SPACE_POSZ = 0.0;
    public static final float VENUS_SPACE_SIZE = 0.5f;
    public static final double VENUS_HEIGHT = 128.0;
    // EARTH
    public static final double OVERWORLD_SPACE_POSX = 0.0;
    public static final double OVERWORLD_SPACE_POSY = 12.0;
    public static final double OVERWORLD_SPACE_POSZ = 0.0;
    public static final float OVERWORLD_SPACE_SIZE = 0.5f;
    public static final double OVERWORLD_HEIGHT = 128.0;

    // MOON
    public static final double MOON_SPACE_POSX = 0.0;
    public static final double MOON_SPACE_POSY = 18.0;
    public static final double MOON_SPACE_POSZ = 0.0;
    public static final float MOON_SPACE_SIZE = 0.5f;
    public static final double MOON_HEIGHT = 128.0;

    // Mars
    public static final double MARS_SPACE_POSX = 0.0;
    public static final double MARS_SPACE_POSY = 24.0;
    public static final double MARS_SPACE_POSZ = 0.0;
    public static final float MARS_SPACE_SIZE = 0.5f;
    public static final double MARS_HEIGHT = 128.0;



    // Misc config
    public static final double PLANET_3D_RENDER_DIST = 1000.0; // Distance at which planets go from 3D to 2D
    // Actually creating the planets

    public static Planet MERCURY = new Planet(
            MERCURY_SPACE_SIZE,
            MERCURY_SPACE_POSX,
            MERCURY_SPACE_POSY,
            MERCURY_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/planets/mercury.png"),
            new Identifier("propulsive:textures/planets/mercury_icon.png")
    );

    public static Planet VENUS = new Planet(
            VENUS_SPACE_SIZE,
            VENUS_SPACE_POSX,
            VENUS_SPACE_POSY,
            VENUS_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/planets/venus.png"),
            new Identifier("propulsive:textures/planets/venus_icon.png")
    );
    public static Planet EARTH = new Planet(
            OVERWORLD_SPACE_SIZE,
            OVERWORLD_SPACE_POSX,
            OVERWORLD_SPACE_POSY,
            OVERWORLD_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/planets/earth.png"),
            new Identifier("propulsive:textures/planets/earth_icon.png")
    );
    public static Planet MOON = new Planet(
            MOON_SPACE_SIZE,
            MOON_SPACE_POSX,
            MOON_SPACE_POSY,
            MOON_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/planets/moon.png"),
            new Identifier("propulsive:textures/planets/moon_icon.png")
    );
    public static Planet MARS = new Planet(
            MARS_SPACE_SIZE,
            MARS_SPACE_POSX,
            MARS_SPACE_POSY,
            MARS_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/planets/mars.png"),
            new Identifier("propulsive:textures/planets/mars_icon.png")
    );

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
