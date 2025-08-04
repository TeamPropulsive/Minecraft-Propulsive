package io.github.teampropulsive;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;


public class Propulsive implements ModInitializer {

    public static RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, new Identifier("propulsive:space"));

    @Override
    public void onInitialize() {

    }

    public static Identifier id(String path) {
        return new Identifier("propulsive", path);
    }
}
