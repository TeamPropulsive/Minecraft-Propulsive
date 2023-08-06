package io.github.teampropulsive.mixin;

import io.github.teampropulsive.handler.DimensionHandler;
import io.github.teampropulsive.handler.LifeSupportHandler;
import io.github.teampropulsive.Propulsive;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        ServerPlayerEntity self = (ServerPlayerEntity)(Object)this;
        if (self.getY() > Propulsive.OVERWORLD_HEIGHT)
            // TODO map overworld coordinates -> space coordinates
            DimensionHandler.TeleportDimension(self, self.server, Propulsive.SPACE, 0, 0, 0);

        LifeSupportHandler.LifeSupport(self);
    }

}