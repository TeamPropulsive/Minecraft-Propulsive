package io.github.teampropulsive.mixin;

import io.github.teampropulsive.handler.EntityGravityHandler;
import io.github.teampropulsive.handler.LifeSupportHandler;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Entity.class)
public class EntityMixin {
    // TODO use setNoGravity() instead of replacing this return value
    @Inject(method = "hasNoGravity", at = @At("RETURN"), cancellable = true)
    private void hasNoGravity(CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity)(Object) this;
        cir.setReturnValue(cir.getReturnValue() || EntityGravityHandler.currentGravity(self) == 0f);
    }

    // Prevents the NoGravity tag from being modified while in space
    // Otherwise gravity will be permanently disabled after entering space
    @Redirect(method = "writeNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putBoolean(Ljava/lang/String;Z)V"))
    private void putBoolean(NbtCompound nbt, String key, boolean value) {
        if (Objects.equals(key, "NoGravity")) {
            Entity self = (Entity)(Object) this;
            if (EntityGravityHandler.currentGravity(self) == 0f)
                return;
        }
        nbt.putBoolean(key, value);
    }
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        Entity self = (Entity)(Object) this;
        LifeSupportHandler.LifeSupport(self);
    }
}
