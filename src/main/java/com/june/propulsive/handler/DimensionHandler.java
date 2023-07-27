package com.june.propulsive.handler;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class DimensionHandler {
    // Mostly temporary, need to calculate position
    public static void TeleportDimension(ServerPlayerEntity player, RegistryKey<World> world, double x, double y, double z) {
        player.teleport(player.server.getWorld(world), x, y, z, player.getYaw(), player.getPitch());
    }
}