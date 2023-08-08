package io.github.teampropulsive;

import io.github.teampropulsive.celestial.Star;
import io.github.teampropulsive.celestial.Terrestrial;
import io.github.teampropulsive.types.Planet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;


public class Propulsive implements ModInitializer {
    public static ArrayList<Planet> TICKABLE_PLANETS = new ArrayList<>();
    @Override
    public void onInitialize() {

        if (FabricLoader.getInstance().isModLoaded("ad_astra")) {
            // Ad Astra compatibility
        }
        if (FabricLoader.getInstance().isModLoaded("galacticraft")) {
            // GC5 Compatibility
        }

        TICKABLE_PLANETS.add(SUN);
        TICKABLE_PLANETS.add(MOON);
        TICKABLE_PLANETS.add(EARTH);
        SUN.parent = EARTH;
        MOON.parent = EARTH;

        Blocks.register();
        Items.register();
    }

    // Dimensions
    public static RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, new Identifier("propulsive:space"));

    public static final double OVERWORLD_HEIGHT = 128.0;

    // Misc config
    public static final double PLANET_3D_RENDER_DIST = 1000.0; // Distance at which planets go from 3D to 2D
    // Actually creating the planets
    public static Terrestrial EARTH = new Terrestrial(
            10.0,
            0.0,
            0.0,
            100.0,
            0.0f,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/earth_icon.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/earth.png")
    );

        public static Star SUN = new Star(
            10.5,
            0.0,
            0.0,
            0.0,
            50.0f,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/sun_icon.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/sun.png")
    );
    public static Terrestrial MOON = new Terrestrial(
            5.0,
            0.0,
            0.0,
            50.0,
            50.0f,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/moon_icon.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/moon.png")
    );

    public static RegistryKey<DamageType> STAR_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("star"));
    public static RegistryKey<DamageType> OXYGEN_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("oxygen"));

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
