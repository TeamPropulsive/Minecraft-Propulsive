package com.june.propulsive.util;

import com.june.propulsive.Propulsive;
import com.mojang.serialization.Dynamic;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.chunk.BlendingData;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashSet;
import java.util.List;

public class ChunkUtil {
    private static final HashSet<ChunkPos> IGNORED_CHUNKS = new HashSet<>();

    public static void copyChunkBlocksAndBlend(Chunk src, Chunk dst, int rotation) {
        ChunkSection[] srcArray = src.getSectionArray();
        ChunkSection[] dstArray = dst.getSectionArray();

        if (srcArray.length != dstArray.length) {
            throw new IllegalArgumentException("cannot copy chunk: chunks must have the same world height!");
        }

        for (int sectionIdx = 0; sectionIdx < srcArray.length; ++sectionIdx) {
            ChunkSection srcSection = srcArray[sectionIdx];
            ChunkSection dstSection = dstArray[sectionIdx];

            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 16; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        int dstX = x;
                        int dstZ = z;

                        switch (rotation) {
                            case 0 -> {}
                            case 1 -> {
                                int tmp = dstX;
                                dstX = dstZ;
                                dstZ = tmp;
                            }
                            case 2 -> {
                                dstX = 15 - dstX;
                                dstZ = 15 - dstZ;
                            }
                            case 3 -> {
                                int tmp = dstX;
                                dstX = 15 - dstZ;
                                dstZ = 15 - tmp;
                            }
                            default -> throw new IllegalArgumentException("cannot copy chunk: invalid rotation");
                        }
                        dstSection.setBlockState(dstX, y, dstZ, srcSection.getBlockState(x, y, z).rotate(switch (rotation) {
                            case 0 -> BlockRotation.NONE;
                            case 1 -> BlockRotation.COUNTERCLOCKWISE_90;
                            case 2 -> BlockRotation.CLOCKWISE_180;
                            case 3 -> BlockRotation.CLOCKWISE_90;
                            default -> throw new IllegalArgumentException("cannot copy chunk: invalid rotation");
                        }));
                    }
                }
            }
        }

        NbtCompound compound = new NbtCompound();
        compound.putBoolean("old_noise", true);
        dst.setBlendingData(BlendingData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound)).resultOrPartial(_msg -> {}).orElse(dst.getBlendingData()));
    }

    private static List<Triple<RegistryKey<World>, ChunkPos, Integer>> getInterdimensionalEquivalents(RegistryKey<World> currentDimension, ChunkPos chunk) {
        return null; // TODO
    }

    public static void registerLoadEvent() {
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            if (!Propulsive.EARTH_DIMENSIONS.isOneOf(world.getRegistryKey()) || chunk.getBlendingData() != null || IGNORED_CHUNKS.contains(chunk.getPos())) {
                return;
            }

            NbtCompound compound = new NbtCompound();
            compound.putBoolean("old_noise", true);
            chunk.setBlendingData(BlendingData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound)).resultOrPartial(_msg -> {}).orElse(chunk.getBlendingData()));

            getInterdimensionalEquivalents(world.getRegistryKey(), chunk.getPos()).forEach(triple -> {
                IGNORED_CHUNKS.add(triple.getMiddle());
                ServerWorld destWorld = world.getServer().getWorld(triple.getLeft());
                Chunk destChunk = destWorld.getChunk(triple.getMiddle().x, triple.getMiddle().z);
                copyChunkBlocksAndBlend(chunk, destChunk, triple.getRight());
                IGNORED_CHUNKS.remove(triple.getMiddle());
            });
        });
    }
}
