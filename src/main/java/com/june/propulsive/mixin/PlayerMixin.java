package com.june.propulsive.mixin;

import com.june.propulsive.handler.DimensionHandler;
import com.june.propulsive.handler.GravityHandler;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.june.propulsive.Propulsive.*;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerMixin {
    @Shadow @Final public MinecraftServer server;
    @Shadow public abstract ServerWorld getServerWorld();

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        List<ServerPlayerEntity> players = this.server.getPlayerManager().getPlayerList();
        for (ServerPlayerEntity player : players) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            // Handles the gravity changes on different planets
            GravityHandler.CalculateGravity(player);

            // Checks if a player leaves the planet or not
            // TODO : Make it work on entities too
            int faceX = (int) (x / OVERWORLD_FACE_SIZE);
            int faceZ = (int) (z / OVERWORLD_FACE_SIZE);

            ServerWorld serverWorld = this.getServerWorld();
            RegistryKey<World> registryKey = serverWorld.getRegistryKey();
            if (registryKey == World.OVERWORLD && y > OVERWORLD_HEIGHT) {
                double preciseFaceX = (x / OVERWORLD_FACE_SIZE) - faceX;
                double preciseFaceZ = (z / OVERWORLD_FACE_SIZE) - faceZ;
                double relativeCubicPosX = 0.0;
                double relativeCubicPosY = 0.0;
                double relativeCubicPosZ = 0.0;

                switch (faceX) {
                    case 0:
                        switch (faceZ) {
                            case 0: // Top
                                relativeCubicPosY = 1.0;
                                relativeCubicPosX = preciseFaceX;
                                relativeCubicPosZ = preciseFaceZ;
                                break;
                            case 1: // Left
                                relativeCubicPosZ = 1.0;
                                relativeCubicPosX = preciseFaceX;
                                relativeCubicPosY = preciseFaceZ;
                                break;
                            case -1: // Right
                                relativeCubicPosZ = -1.0;
                                relativeCubicPosX = -preciseFaceX;
                                relativeCubicPosY = -preciseFaceZ;
                                break;
                            default:
                                System.err.println("Invalid chunk! ");
                                return;
                        }
                        break;
                    case -1: // Front
                        relativeCubicPosX = 1.0;
                        relativeCubicPosY = preciseFaceX;
                        relativeCubicPosZ = preciseFaceZ;
                        break;
                    case 1: // Back
                        relativeCubicPosX = -1.0;
                        relativeCubicPosY = -preciseFaceX;
                        relativeCubicPosZ = -preciseFaceZ;
                        break;
                    case 2: // Base
                        relativeCubicPosY = -1.0;
                        relativeCubicPosX = -preciseFaceX;
                        relativeCubicPosZ = -preciseFaceZ;
                        break;
                    default:
                        System.err.println("Invalid chunk! ");
                        return;
                }

                DimensionHandler.TeleportDimension(
                        player,
                        SPACE,
                        OVERWORLD_SPACE_POSX + (relativeCubicPosX * OVERWORLD_SPACE_SIZE),
                        OVERWORLD_SPACE_POSY + (relativeCubicPosY * OVERWORLD_SPACE_SIZE),
                        OVERWORLD_SPACE_POSZ + (relativeCubicPosZ * OVERWORLD_SPACE_SIZE)
                );
            }

        }

    }
}