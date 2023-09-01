package io.github.teampropulsive.celestial;

import io.github.teampropulsive.types.AtmoCompositionGas;
import io.github.teampropulsive.types.Planet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
public class Terrestrial extends Planet {
    public Terrestrial(double scale, Vec3d pos, double orbitTime, double rotationTime, Vec2f rotationAngle, Identifier texture2d, Identifier texture3d, AtmoCompositionGas[] composition) {
        super(scale, pos, orbitTime, rotationTime, rotationAngle, texture2d, texture3d, composition);
    }

    @Override
    public void collisionDetected(ServerPlayerEntity player) {}
}
