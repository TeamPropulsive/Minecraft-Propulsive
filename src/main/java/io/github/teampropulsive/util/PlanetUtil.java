package io.github.teampropulsive.util;

import io.github.teampropulsive.types.Planet;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import static io.github.teampropulsive.Propulsive.TICKABLE_PLANETS;

public class PlanetUtil {
    public Planet getPlanetFromDim(RegistryKey<World> world) {
        for (Planet planet : TICKABLE_PLANETS) {
            if (planet.dimensions.isOneOf(world)) {
                return planet;
            }
        }
        throw new IllegalArgumentException("provided dimension is not part of a planet");
    }
}
