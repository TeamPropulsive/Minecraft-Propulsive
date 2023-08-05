package com.june.propulsive;

import com.june.propulsive.celestial.Gas;
import com.june.propulsive.celestial.Star;
import com.june.propulsive.celestial.Terrestrial;
import com.june.propulsive.types.Planet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;

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

        TickablePlanets.add(EARTH);
        TickablePlanets.add(SUN);
        EARTH.parent = SUN;

        Block.register();
        Item.register();
    }

    // Dimensions
    public static RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, new Identifier("propulsive:space"));

    public static final double OVERWORLD_HEIGHT = 128.0;

    // Misc config
    public static final double PLANET_3D_RENDER_DIST = 1000.0; // Distance at which planets go from 3D to 2D
    // Actually creating the planets
    public static Star SUN = new Star(
            10.0,
            0.0,
            0.0,
            0.0,
            0.0f,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/star/sun.png"),
            new Identifier("propulsive:textures/celestial/star/sun_icon.png")
    );
    public static Terrestrial EARTH = new Terrestrial(
            5.0,
            0.0,
            0.0,
            50.0,
            200.0f,
            0.0f,
            0.0f,
            new Identifier("propulsive:textures/celestial/terrestrial/earth.png"),
            new Identifier("propulsive:textures/celestial/terrestrial/earth_icon.png")
    );

    public static RegistryKey<DamageType> STAR_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("star"));

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
