package io.github.teampropulsive.mixin;

import io.github.teampropulsive.types.Planet;
import io.github.teampropulsive.Propulsive;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class PlanetMixin {
    @Shadow @Final private MinecraftServer server;

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) { for (Planet planet : Propulsive.TICKABLE_PLANETS) {
        planet.tick(this.server);
    }
    }
}
