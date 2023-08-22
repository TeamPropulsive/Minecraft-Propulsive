package io.github.teampropulsive.mixin;

import io.github.teampropulsive.handler.DimensionHandler;
import io.github.teampropulsive.handler.LifeSupportHandler;
import io.github.teampropulsive.Propulsive;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.teampropulsive.Propulsive.EARTH;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        ServerPlayerEntity self = (ServerPlayerEntity)(Object)this;
        if (self.getY() > Propulsive.OVERWORLD_HEIGHT)
            EARTH.getSpacePos(self.getPos(), self.getWorld().getRegistryKey());
        LifeSupportHandler.LifeSupport(self);
    }

}