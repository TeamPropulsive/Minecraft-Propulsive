package io.github.teampropulsive;

import io.github.teampropulsive.celestial.Star;
import io.github.teampropulsive.celestial.Terrestrial;
import io.github.teampropulsive.types.Planet;
import io.github.teampropulsive.types.PlanetDimensions;
import io.github.teampropulsive.util.ChunkUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import qouteall.q_misc_util.LifecycleHack;
import qouteall.q_misc_util.api.DimensionAPI;

import java.util.ArrayList;


public class Propulsive implements ModInitializer {
    public static ArrayList<Planet> TICKABLE_PLANETS = new ArrayList<>();
    @SuppressWarnings("StatementWithEmptyBody")
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
        EARTH.parent = SUN;
        MOON.parent = EARTH;

        // prevent experimental features warning when creating world
        LifecycleHack.markNamespaceStable("propulsive");

        DimensionAPI.serverDimensionsLoadEvent.register((generatorOptions, registryManager) -> {
            Registry<DimensionOptions> levelStemRegistry = registryManager.get(RegistryKeys.DIMENSION);
            DimensionOptions levelStem = levelStemRegistry.get(DimensionOptions.OVERWORLD);
            RegistryEntry<DimensionType> dimTypeEntry = levelStem.dimensionTypeEntry();
            ChunkGenerator overworldGenerator = levelStem.chunkGenerator();

            DimensionAPI.addDimension(levelStemRegistry, DIM_OW_TOP.getValue(), dimTypeEntry, overworldGenerator);
            DimensionAPI.addDimension(levelStemRegistry, DIM_OW_BOTTOM.getValue(), dimTypeEntry, overworldGenerator);
            DimensionAPI.addDimension(levelStemRegistry, DIM_OW_LEFT.getValue(), dimTypeEntry, overworldGenerator);
            DimensionAPI.addDimension(levelStemRegistry, DIM_OW_RIGHT.getValue(), dimTypeEntry, overworldGenerator);
            DimensionAPI.addDimension(levelStemRegistry, DIM_OW_BACK.getValue(), dimTypeEntry, overworldGenerator);

            DIMENSIONS_LOADED = true;
        });

        Blocks.register();
        Items.register();

        ChunkUtil.registerLoadEvent();
    }

    // Dimensions
    public static RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, id("space"));

    // Overworld faces
    public static RegistryKey<World> DIM_OW_FRONT = World.OVERWORLD;
    public static RegistryKey<World> DIM_OW_TOP = RegistryKey.of(RegistryKeys.WORLD, id("ow_top"));
    public static RegistryKey<World> DIM_OW_BOTTOM = RegistryKey.of(RegistryKeys.WORLD, id("ow_bottom"));
    public static RegistryKey<World> DIM_OW_LEFT = RegistryKey.of(RegistryKeys.WORLD, id("ow_left"));
    public static RegistryKey<World> DIM_OW_RIGHT = RegistryKey.of(RegistryKeys.WORLD, id("ow_right"));
    public static RegistryKey<World> DIM_OW_BACK = RegistryKey.of(RegistryKeys.WORLD, id("ow_back"));

    public static PlanetDimensions EARTH_DIMENSIONS = new PlanetDimensions(DIM_OW_TOP, DIM_OW_BOTTOM, DIM_OW_LEFT, DIM_OW_RIGHT, DIM_OW_FRONT, DIM_OW_BACK, 1024);
    public static boolean DIMENSIONS_LOADED = false;

    public static final double OVERWORLD_HEIGHT = 128.0;

    // Misc config
    public static final double PLANET_3D_RENDER_DIST = 1000.0; // Distance at which planets go from 3D to 2D
    // Actually creating the planets
    public static Terrestrial EARTH = new Terrestrial(
            10.0,
            0.0,
            0.0,
            100.0,
            50.0f,
            0.0f,
            0.0f,
            Propulsive.id("textures/celestial/terrestrial/earth_icon.png"),
            Propulsive.id("textures/celestial/terrestrial/earth.png"),
            EARTH_DIMENSIONS
    );

        public static Star SUN = new Star(
            10.5,
            0.0,
            0.0,
            0.0,
            50.0f,
            0.0f,
            0.0f,
            Propulsive.id("textures/celestial/star/sun_icon.png"),
            Propulsive.id("textures/celestial/star/sun.png"),
            null
    );
    public static Terrestrial MOON = new Terrestrial(
            5.0,
            0.0,
            0.0,
            50.0,
            50.0f,
            0.0f,
            0.0f,
            Propulsive.id("textures/celestial/star/sun_icon.png"),
            Propulsive.id("textures/celestial/star/sun.png"),
            null
    );

    public static RegistryKey<DamageType> STAR_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("star"));
    public static RegistryKey<DamageType> OXYGEN_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("oxygen"));

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
