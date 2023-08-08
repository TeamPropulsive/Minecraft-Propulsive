package io.github.teampropulsive.celestial;

import io.github.teampropulsive.types.Planet;
import io.github.teampropulsive.types.PlanetDimensions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

import static com.june.propulsive.Propulsive.SPACE;

public class Terrestrial extends Planet {
    public Terrestrial(double scale, double posX, double posY, double posZ, float orbitTime, float horizontalRotation, float verticalRotation, Identifier texture2d, Identifier texture3d, PlanetDimensions dimensions) {
        super(scale, posX, posY, posZ, orbitTime, horizontalRotation, verticalRotation, texture2d, texture3d, dimensions);
    }

    @Override
    public void collisionDetected(ServerPlayerEntity player) {}
}
