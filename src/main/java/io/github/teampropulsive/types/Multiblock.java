package io.github.teampropulsive.types;

import net.minecraft.predicate.BlockPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Map;

public class Multiblock {
    private final Map<BlockPos, BlockPredicate> predicates;

    public Multiblock(Map<BlockPos, BlockPredicate> predicates) {
        this.predicates = predicates;
    }

    // why must you make me do this mojang
    private static BlockPos addPos(BlockPos a, BlockPos b) {
        return a.offset(Direction.Axis.X, b.getX()).offset(Direction.Axis.Y, b.getY()).offset(Direction.Axis.Z, b.getZ());
    }

    public boolean check(ServerWorld world, BlockPos center) {
        for (Map.Entry<BlockPos, BlockPredicate> entry : this.predicates.entrySet()) {
            BlockPos offset = entry.getKey();
            if (!entry.getValue().test(world, addPos(center, offset))) {
                return false;
            }
        }
        return true;
    }
}
