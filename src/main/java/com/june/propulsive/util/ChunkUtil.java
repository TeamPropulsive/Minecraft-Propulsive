package com.june.propulsive.util;

import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.chunk.BlendingData;

public class ChunkUtil {
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
                        dstSection.setBlockState(dstX, y, dstZ, srcSection.getBlockState(x, y, z));
                    }
                }
            }
        }

        NbtCompound compound = new NbtCompound();
        compound.putBoolean("old_noise", true);
        dst.setBlendingData(BlendingData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound)).resultOrPartial(_msg -> {}).orElse(dst.getBlendingData()));
    }
}
