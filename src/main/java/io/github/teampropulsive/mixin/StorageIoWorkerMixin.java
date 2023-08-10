package io.github.teampropulsive.mixin;

import io.github.teampropulsive.duck.StorageIoWorkerDuck;
import io.github.teampropulsive.types.PlanetDimensions;
import io.github.teampropulsive.util.ChunkUtil;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.StorageIoWorker;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.BitSet;
import java.util.concurrent.CompletableFuture;

@Mixin(StorageIoWorker.class)
public class StorageIoWorkerMixin implements StorageIoWorkerDuck {
    @Unique private RegistryKey<World> world;
    @Unique private PlanetDimensions planet;

    @Redirect(method = "method_42331", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/storage/StorageIoWorker;needsBlending(Lnet/minecraft/nbt/NbtCompound;)Z"))
    private boolean forceBlendingForOutOfBoundsChunks(StorageIoWorker instance, NbtCompound nbt, BitSet bitSet, ChunkPos chunkPos) {
        if (world == null || planet == null || !ChunkUtil.DEST_CHUNKS.contains(Pair.of(world, chunkPos))) {
            return ((StorageIoWorkerInvoker)instance).invokeNeedsBlending(nbt);
        }

        System.out.println("forcing blending for chunk " + chunkPos.toString() + " in world " + world.getValue().toString());
        
        BlockPos unOffset = chunkPos.getCenterAtY(0).add(-planet.getOffset(world), 0, 0);
        return Math.abs(unOffset.getX()) >= planet.faceRadius() || Math.abs(unOffset.getZ()) >= planet.faceRadius();
    }

    @Redirect(method = "getOrComputeBlendingStatus", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectLinkedOpenHashMap;getAndMoveToFirst(J)Ljava/lang/Object;"))
    private Object /* CompletableFuture<BitSet> */ disableCacheForOutOfBoundsChunks(Long2ObjectLinkedOpenHashMap<CompletableFuture<BitSet>> instance, long k) {
        if (world == null || planet == null) {
            return instance.getAndMoveToFirst(k);
        }

        BlockPos unOffset = new ChunkPos(k).getCenterAtY(0).add(-planet.getOffset(world), 0, 0);
        return (Math.abs(unOffset.getX()) >= planet.faceRadius() && Math.abs(unOffset.getX()) <= planet.faceRadius() + 32)
                || (Math.abs(unOffset.getZ()) >= planet.faceRadius() && Math.abs(unOffset.getZ()) <= planet.faceRadius() + 32)
                ? null : instance.getAndMoveToFirst(k);
    }

    @Override
    @Unique
    public void setWorld(RegistryKey<World> world) {
        this.world = world;
    }

    @Override
    @Unique
    public void setPlanet(PlanetDimensions planet) {
        this.planet = planet;
    }
}
