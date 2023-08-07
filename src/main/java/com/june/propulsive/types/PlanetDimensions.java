package com.june.propulsive.types;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public record PlanetDimensions(
        RegistryKey<World> top,
        RegistryKey<World> bottom,
        RegistryKey<World> left,
        RegistryKey<World> right,
        RegistryKey<World> front,
        RegistryKey<World> back
) {}
