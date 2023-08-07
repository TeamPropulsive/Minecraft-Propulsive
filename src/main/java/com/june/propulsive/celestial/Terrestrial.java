package com.june.propulsive.celestial;

import com.june.propulsive.types.Planet;
import com.june.propulsive.types.PlanetDimensions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class Terrestrial extends Planet {
    public Terrestrial(double scale, double posX, double posY, double posZ, float orbitTime, float horizontalRotation, float verticalRotation, Identifier texture2d, Identifier texture3d, PlanetDimensions dimensions) {
        super(scale, posX, posY, posZ, orbitTime, horizontalRotation, verticalRotation, texture2d, texture3d, dimensions);
    }

    @Override
    public void collisionDetected(ServerPlayerEntity player) {

    }
}
