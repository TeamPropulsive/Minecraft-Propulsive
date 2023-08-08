package io.github.teampropulsive.celestial;

import io.github.teampropulsive.types.Planet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import qouteall.q_misc_util.my_util.Vec2d;

public class Terrestrial extends Planet {
    public Terrestrial(double scale, Vec3d pos, double orbitTime, double rotationTime, Vec2d rotationAngle, Identifier texture2d, Identifier texture3d) {
        super(scale, pos, orbitTime, rotationTime, rotationAngle, texture2d, texture3d);
    }

    @Override
    public void collisionDetected(ServerPlayerEntity player) {}
}
