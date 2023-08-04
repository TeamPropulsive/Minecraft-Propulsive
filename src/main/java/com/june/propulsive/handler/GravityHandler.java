package com.june.propulsive.handler;

import net.minecraft.entity.Entity;

import static com.june.propulsive.Propulsive.SPACE;

public class GravityHandler {
    public static float currentGravity(Entity entity) {
        if (entity.getWorld().getRegistryKey() == SPACE)
            return 0f;
        return 1f;
    }
}
