package io.github.teampropulsive.mixin;

import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.duck.StorageIoWorkerDuck;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Shadow @Final private ServerChunkManager chunkManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void setStorageIoWorkerFaceRadius(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime, RandomSequencesState randomSequencesState, CallbackInfo ci) {
        if (Propulsive.EARTH_DIMENSIONS.isOneOf(worldKey)) {
            StorageIoWorkerDuck worker = ((StorageIoWorkerDuck)this.chunkManager.threadedAnvilChunkStorage.getWorker());
            worker.setWorld(worldKey);
            worker.setPlanet(Propulsive.EARTH_DIMENSIONS);
        }
    }
}
