package com.june.propulsive.mixin;

import com.june.propulsive.types.Planet;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.june.propulsive.Propulsive.TickablePlanets;

@Mixin(ServerWorld.class)
public class PlanetMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) { for (Planet planet : TickablePlanets) planet.tick(); }
}
