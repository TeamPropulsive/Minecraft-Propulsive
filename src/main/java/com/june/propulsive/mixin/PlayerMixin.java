package com.june.propulsive.mixin;

import com.june.propulsive.handler.DimensionHandler;
import com.june.propulsive.handler.LifeSupportHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.june.propulsive.Propulsive.OVERWORLD_HEIGHT;
import static com.june.propulsive.Propulsive.SPACE;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        ServerPlayerEntity self = (ServerPlayerEntity)(Object)this;
        if (self.getY() > OVERWORLD_HEIGHT)
            // TODO map overworld coordinates -> space coordinates
            DimensionHandler.TeleportDimension(self, self.server, SPACE, 0, 0, 0);

        LifeSupportHandler.LifeSupport(self);
    }

}