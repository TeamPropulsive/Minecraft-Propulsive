package com.june.propulsive.handler;

import net.minecraft.entity.Entity;

import static com.june.propulsive.Propulsive.SPACE;

public class EntityGravityHandler {
    public static float currentGravity(Entity entity) {
        if (entity.getWorld().getRegistryKey() == SPACE && !entity.isInSneakingPose())
            return 0f;
        else if (entity.getWorld().getRegistryKey() == SPACE && entity.isInSneakingPose())
            return 1f;
        return 1f;
    }
}
