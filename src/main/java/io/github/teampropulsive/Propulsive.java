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

    @Override
    public void onInitialize() {

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
