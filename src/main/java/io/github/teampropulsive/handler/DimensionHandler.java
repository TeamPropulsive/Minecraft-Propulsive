package io.github.teampropulsive.handler;

import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class DimensionHandler {
    // Mostly temporary, need to calculate position
    public static void TeleportDimension(Entity entity, MinecraftServer server, RegistryKey<World> world, double x, double y, double z) {
        entity.teleport(server.getWorld(world), x, y, z, null, entity.getYaw(), entity.getPitch());
    }
}