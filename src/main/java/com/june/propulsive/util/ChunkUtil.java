package com.june.propulsive.util;

import com.june.propulsive.Propulsive;
import com.june.propulsive.types.PlanetDimensions;
import com.mojang.serialization.Dynamic;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.chunk.BlendingData;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static List<Triple<RegistryKey<World>, ChunkPos, Integer>> getInterdimensionalEquivalents(RegistryKey<World> currentDimension, PlanetDimensions dimensions, ChunkPos chunk) {
        Map<Pair<RegistryKey<World>, Direction>, Pair<RegistryKey<World>, Direction>> cubeMap = dimensions.getCubeMap();
        int offsetBlocks = dimensions.faceRadius() * dimensions.getOffset(currentDimension);
        BlockPos withoutOffset = chunk.getCenterAtY(0).add(-offsetBlocks, 0, 0);

        HashSet<Direction> directions = new HashSet<>();
        if (withoutOffset.getX() + 32 > dimensions.faceRadius()) {
            directions.add(Direction.SOUTH);
        }
        if (withoutOffset.getX() - 32 < -dimensions.faceRadius()) {
            directions.add(Direction.NORTH);
        }
        if (withoutOffset.getZ() + 32 > dimensions.faceRadius()) {
            directions.add(Direction.EAST);
        }
        if (withoutOffset.getZ() - 32 < -dimensions.faceRadius()) {
            directions.add(Direction.WEST);
        }

        return directions.stream().map(dir -> {
            Pair<RegistryKey<World>, Direction> otherFace = cubeMap.get(Pair.of(currentDimension, dir));
            int rotation = MathUtil.mod(dir.getHorizontal() - 2 - otherFace.getRight().getHorizontal(), 4);

            BlockPos rotated = withoutOffset.rotate(switch (rotation) {
                case 0 -> BlockRotation.NONE;
                case 1 -> BlockRotation.COUNTERCLOCKWISE_90;
                case 2 -> BlockRotation.CLOCKWISE_180;
                case 3 -> BlockRotation.CLOCKWISE_90;
                default -> throw new IllegalStateException("invalid rotation");
            });
            int offsetX = dimensions.getOffset(otherFace.getLeft()), offsetZ = 0;
            switch (otherFace.getRight()) {
                case NORTH -> offsetX -= dimensions.faceRadius() * 2;
                case SOUTH -> offsetX += dimensions.faceRadius() * 2;
                case EAST -> offsetZ += dimensions.faceRadius() * 2;
                case WEST -> offsetZ -= dimensions.faceRadius() * 2;
                default -> throw new IllegalStateException("invalid direction");
            }

            return Triple.of(otherFace.getLeft(), new ChunkPos(rotated.add(offsetX, 0, offsetZ)), rotation);
        }).collect(Collectors.toList());
    }

    public static void registerLoadEvent() {
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            if (!Propulsive.DIMENSIONS_LOADED || !Propulsive.EARTH_DIMENSIONS.isOneOf(world.getRegistryKey()) || chunk.usesOldNoise() || IGNORED_CHUNKS.contains(chunk.getPos())) {
                return;
            }

            System.out.println("attempting to copy chunk " + chunk.getPos().toString() + " in " + world.getRegistryKey().getValue().toString());

            NbtCompound compound = new NbtCompound();
            compound.putBoolean("old_noise", true);
            chunk.setBlendingData(BlendingData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, compound)).resultOrPartial(_msg -> {}).orElse(chunk.getBlendingData()));

            getInterdimensionalEquivalents(world.getRegistryKey(), Propulsive.EARTH_DIMENSIONS, chunk.getPos()).forEach(triple -> {
                IGNORED_CHUNKS.add(triple.getMiddle());
                ServerWorld destWorld = world.getServer().getWorld(triple.getLeft());
                System.out.println("copying " + chunk.getPos().toString() + " in " + world.getRegistryKey().getValue().toString() + " to " + triple.getMiddle().toString() + " in " + triple.getLeft().getValue().toString());
                Chunk destChunk = destWorld.getChunk(triple.getMiddle().x, triple.getMiddle().z);
                copyChunkBlocksAndBlend(chunk, destChunk, triple.getRight());
                IGNORED_CHUNKS.remove(triple.getMiddle());
            });
        });
    }
}
