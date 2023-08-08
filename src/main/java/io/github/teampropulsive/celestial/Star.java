package io.github.teampropulsive.celestial;

import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.types.Planet;
import io.github.teampropulsive.types.PlanetDimensions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class Star extends Planet {
    public Star(double scale, double posX, double posY, double posZ, float orbitTime, float horizontalRotation, float verticalRotation, Identifier texture2d, Identifier texture3d, PlanetDimensions dimensions) {
        super(scale, posX, posY, posZ, orbitTime, horizontalRotation, verticalRotation, texture2d, texture3d, dimensions);
    }
    @Override
    public void collisionDetected(ServerPlayerEntity player) {
        player.damage(player.getWorld().getDamageSources().create(Propulsive.STAR_DAMAGE_TYPE), Float.MAX_VALUE);
    }
}
