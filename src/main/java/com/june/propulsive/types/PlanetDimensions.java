package com.june.propulsive.types;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

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
                Map.entry(new Pair<>(front, SOUTH), new Pair<>(top, NORTH)),
                Map.entry(new Pair<>(front, EAST), new Pair<>(right, WEST)),
                Map.entry(new Pair<>(front, NORTH), new Pair<>(bottom, SOUTH)),
                Map.entry(new Pair<>(front, WEST), new Pair<>(left, EAST)),

                Map.entry(new Pair<>(back, SOUTH), new Pair<>(bottom, NORTH)),
                Map.entry(new Pair<>(back, EAST), new Pair<>(right, EAST)),
                Map.entry(new Pair<>(back, NORTH), new Pair<>(top, SOUTH)),
                Map.entry(new Pair<>(back, WEST), new Pair<>(left, WEST)),

                Map.entry(new Pair<>(top, SOUTH), new Pair<>(back, NORTH)),
                Map.entry(new Pair<>(top, EAST), new Pair<>(right, SOUTH)),
                Map.entry(new Pair<>(top, NORTH), new Pair<>(front, SOUTH)),
                Map.entry(new Pair<>(top, WEST), new Pair<>(left, SOUTH)),

                Map.entry(new Pair<>(bottom, SOUTH), new Pair<>(front, NORTH)),
                Map.entry(new Pair<>(bottom, EAST), new Pair<>(right, NORTH)),
                Map.entry(new Pair<>(bottom, NORTH), new Pair<>(back, SOUTH)),
                Map.entry(new Pair<>(bottom, WEST), new Pair<>(left, NORTH)),

                Map.entry(new Pair<>(left, SOUTH), new Pair<>(top, WEST)),
                Map.entry(new Pair<>(left, EAST), new Pair<>(front, WEST)),
                Map.entry(new Pair<>(left, NORTH), new Pair<>(bottom, WEST)),
                Map.entry(new Pair<>(left, WEST), new Pair<>(back, WEST)),

                Map.entry(new Pair<>(right, SOUTH), new Pair<>(top, EAST)),
                Map.entry(new Pair<>(right, EAST), new Pair<>(back, EAST)),
                Map.entry(new Pair<>(right, NORTH), new Pair<>(bottom, EAST)),
                Map.entry(new Pair<>(right, WEST), new Pair<>(front, EAST))
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
