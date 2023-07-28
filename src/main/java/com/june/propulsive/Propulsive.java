package com.june.propulsive;

import com.june.propulsive.celestial.Star;
import com.june.propulsive.celestial.Terrestrial;
import com.june.propulsive.types.Planet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

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

        SUN.render();
        MERCURY.render();
        VENUS.render();
        EARTH.render();
        MOON.render();
        MARS.render();

        MapScreenKeybindRegister();

        TickablePlanets.add(SUN);
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
    public static Star SUN = new Star(
            10.0,
            0.0,
            45.0,
            0.0,
            -87.0f,
            23.0f,
            new Identifier("propulsive:textures/celestial/star/sun.png"),
            new Identifier("propulsive:textures/celestial/star/sun_icon.png")
    );
    public static Terrestrial MERCURY = new Terrestrial(
            MERCURY_SPACE_SIZE,
            MERCURY_SPACE_POSX,
            MERCURY_SPACE_POSY,
            MERCURY_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/mercury.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/mercury_icon.png")
    );

    public static Terrestrial VENUS = new Terrestrial(
            VENUS_SPACE_SIZE,
            VENUS_SPACE_POSX,
            VENUS_SPACE_POSY,
            VENUS_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/venus.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/venus_icon.png")
    );
    public static Terrestrial EARTH = new Terrestrial(
            OVERWORLD_SPACE_SIZE,
            OVERWORLD_SPACE_POSX,
            OVERWORLD_SPACE_POSY,
            OVERWORLD_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/earth.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/earth_icon.png")
    );
    public static Terrestrial MOON = new Terrestrial(
            MOON_SPACE_SIZE,
            MOON_SPACE_POSX,
            MOON_SPACE_POSY,
            MOON_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/moon.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/moon_icon.png")
    );
    public static Terrestrial MARS = new Terrestrial(
            MARS_SPACE_SIZE,
            MARS_SPACE_POSX,
            MARS_SPACE_POSY,
            MARS_SPACE_POSZ,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/mars.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/mars_icon.png")
    );

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
