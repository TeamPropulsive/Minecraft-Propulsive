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
            // TODO : Implement better projection


            if (y > OVERWORLD_HEIGHT) {
                DimensionHandler.TeleportDimension(
                        player,
                        SPACE,
                        0,
                        0,
                        0
                );
            }

            }



    }

}