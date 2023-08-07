package com.june.propulsive.types;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public class PlanetDimensions {
    public RegistryKey<World> top;
    public RegistryKey<World> bottom;
    public RegistryKey<World> left;
    public RegistryKey<World> right;
    public RegistryKey<World> front;
    public RegistryKey<World> back;

    public PlanetDimensions(RegistryKey<World> top, RegistryKey<World> bottom, RegistryKey<World> left, RegistryKey<World> right, RegistryKey<World> back, RegistryKey<World> front) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.front = front;
        this.back = back;
    }

}
