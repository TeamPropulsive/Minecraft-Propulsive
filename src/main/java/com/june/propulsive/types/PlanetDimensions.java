package com.june.propulsive.types;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

import static net.minecraft.util.math.Direction.*;

public record PlanetDimensions(
        RegistryKey<World> top,
        RegistryKey<World> bottom,
        RegistryKey<World> left,
        RegistryKey<World> right,
        RegistryKey<World> front,
        RegistryKey<World> back,
        int faceRadius
) {
    public boolean isOneOf(RegistryKey<World> world) {
        return world.equals(top) || world.equals(bottom) || world.equals(left) || world.equals(right) || world.equals(front) || world.equals(back);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Map<Pair<RegistryKey<World>, Direction>, Pair<RegistryKey<World>, Direction>> getCubeMap() {
        return Map.ofEntries(
                // south is currently up for... reasons
                Map.entry(Pair.of(front, SOUTH), Pair.of(top, NORTH)),
                Map.entry(Pair.of(front, EAST), Pair.of(right, WEST)),
                Map.entry(Pair.of(front, NORTH), Pair.of(bottom, SOUTH)),
                Map.entry(Pair.of(front, WEST), Pair.of(left, EAST)),

                Map.entry(Pair.of(back, SOUTH), Pair.of(bottom, NORTH)),
                Map.entry(Pair.of(back, EAST), Pair.of(right, EAST)),
                Map.entry(Pair.of(back, NORTH), Pair.of(top, SOUTH)),
                Map.entry(Pair.of(back, WEST), Pair.of(left, WEST)),

                Map.entry(Pair.of(top, SOUTH), Pair.of(back, NORTH)),
                Map.entry(Pair.of(top, EAST), Pair.of(right, SOUTH)),
                Map.entry(Pair.of(top, NORTH), Pair.of(front, SOUTH)),
                Map.entry(Pair.of(top, WEST), Pair.of(left, SOUTH)),

                Map.entry(Pair.of(bottom, SOUTH), Pair.of(front, NORTH)),
                Map.entry(Pair.of(bottom, EAST), Pair.of(right, NORTH)),
                Map.entry(Pair.of(bottom, NORTH), Pair.of(back, SOUTH)),
                Map.entry(Pair.of(bottom, WEST), Pair.of(left, NORTH)),

                Map.entry(Pair.of(left, SOUTH), Pair.of(top, WEST)),
                Map.entry(Pair.of(left, EAST), Pair.of(front, WEST)),
                Map.entry(Pair.of(left, NORTH), Pair.of(bottom, WEST)),
                Map.entry(Pair.of(left, WEST), Pair.of(back, WEST)),

                Map.entry(Pair.of(right, SOUTH), Pair.of(top, EAST)),
                Map.entry(Pair.of(right, EAST), Pair.of(back, EAST)),
                Map.entry(Pair.of(right, NORTH), Pair.of(bottom, EAST)),
                Map.entry(Pair.of(right, WEST), Pair.of(front, EAST))
        );
    }

    public int getOffset(RegistryKey<World> side) {
        if (side == front) {
            return 0;
        } else if (side.equals(top)) {
            return 1;
        } else if (side.equals(bottom)) {
            return 2;
        } else if (side.equals(left)) {
            return 3;
        } else if (side.equals(right)) {
            return 4;
        } else if (side.equals(back)) {
            return 5;
        } else {
            throw new IllegalArgumentException("provided dimension is not a valid side of this planet");
        }
    }
}
