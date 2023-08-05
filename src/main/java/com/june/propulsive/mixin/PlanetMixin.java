package com.june.propulsive.mixin;

import com.june.propulsive.types.Planet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.june.propulsive.Propulsive.TICKABLE_PLANETS;

@Mixin(ServerWorld.class)
public class PlanetMixin {
    @Shadow @Final private MinecraftServer server;

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) { for (Planet planet : TICKABLE_PLANETS) {
        planet.tick(this.server);
    }
    }
}
