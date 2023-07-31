package com.june.propulsive.client.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SpaceDimensionEffects extends DimensionEffects {
    public SpaceDimensionEffects() {
        super(Float.NaN, false, SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color;
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return false;
    }


    @Override
    public float @Nullable [] getFogColorOverride(float skyAngle, float tickDelta) {
        return null;
    }
}
