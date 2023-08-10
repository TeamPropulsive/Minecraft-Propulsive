package io.github.teampropulsive.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.storage.StorageIoWorker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StorageIoWorker.class)
public interface StorageIoWorkerInvoker {
    @Invoker
    boolean invokeNeedsBlending(NbtCompound nbt);
}
