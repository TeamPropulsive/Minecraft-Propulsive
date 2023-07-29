package com.june.propulsive.mixin;

import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin {
    @Shadow public abstract void setSize(double size);

    @Inject(at = @At("HEAD"), method = "getSize", cancellable = true)
    public void getSize(CallbackInfoReturnable<Double> cir) {
        double size = getWorldborderSize();
        this.setSize(size);
        cir.setReturnValue(size);
    }

    // Logic will go here
    // Should work with multiple dimensions in theory, might not in practice
    @Unique
    public double getWorldborderSize() {
        return 1000.0;
    }
}