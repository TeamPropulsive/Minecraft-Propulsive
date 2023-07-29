package com.june.propulsive.handler;

import net.minecraft.server.network.ServerPlayerEntity;

import static com.june.propulsive.Propulsive.SPACE;

public class GravityHandler {
    public static void CalculateGravity(ServerPlayerEntity player) {
        if (player.getWorld().getRegistryKey() == SPACE) {
            player.setNoGravity(true);
        }
    }
}
