package io.github.teampropulsive;

import io.github.teampropulsive.celestial.Star;
import io.github.teampropulsive.celestial.Terrestrial;
import io.github.teampropulsive.space.rocket.RocketEntity;
import io.github.teampropulsive.space.spacecraft.SpacecraftEntity;
import io.github.teampropulsive.types.Planet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qouteall.q_misc_util.my_util.Vec2d;

import java.util.ArrayList;


public class Propulsive implements ModInitializer {
    public static ArrayList<Planet> TICKABLE_PLANETS = new ArrayList<>();
    public static final EntityType<RocketEntity> TEST_ROCKET = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("propulsive", "test_rocket"),
            FabricEntityTypeBuilder.create(
                    SpawnGroup.CREATURE,
                    RocketEntity::new
            ).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );
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

        Blocks.register();
        Items.register();

        FabricDefaultAttributeRegistry.register(TEST_ROCKET, RocketEntity.createMobAttributes());
    }

    // Dimensions
    public static RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, new Identifier("propulsive:space"));

    public static final double OVERWORLD_HEIGHT = 128.0;

    // Misc config
    public static final double PLANET_3D_RENDER_DIST = 1000.0; // Distance at which planets go from 3D to 2D
    // Actually creating the planets
    public static Terrestrial EARTH = new Terrestrial(
            10.0, new Vec3d(-50, 0, 0), 50.0, 0,new Vec2d(0, 0),
            Propulsive.id("textures/celestial/terrestrial/earth_icon.png"),
            Propulsive.id("textures/celestial/terrestrial/earth.png")
    );

        public static Star SUN = new Star(
             20, new Vec3d(0, 0, 0), 50.0, 0, new Vec2d(0, 0),
            Propulsive.id("textures/celestial/star/sun_icon.png"),
            Propulsive.id("textures/celestial/star/sun.png")
    );
    public static Terrestrial MOON = new Terrestrial(
            2.5, new Vec3d(-60, 0, 0), 60.0, 0, new Vec2d(0, 0),
            Propulsive.id("textures/celestial/terrestrial/moon_icon.png"),
            Propulsive.id("textures/celestial/terrestrial/moon.png")
    );

    public static RegistryKey<DamageType> STAR_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("star"));
    public static RegistryKey<DamageType> OXYGEN_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("oxygen"));

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
