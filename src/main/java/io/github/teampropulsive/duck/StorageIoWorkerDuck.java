package io.github.teampropulsive.duck;

import io.github.teampropulsive.types.PlanetDimensions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public interface StorageIoWorkerDuck {
    void setWorld(RegistryKey<World> world);
    void setPlanet(PlanetDimensions planet);
}
