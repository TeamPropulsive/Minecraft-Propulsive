package io.github.teampropulsive.util;

import net.minecraft.util.math.BlockPos;

/**
 * Reimplementation of {@link net.minecraft.util.math.BlockBox} because Mojang's code hurts my soul.
 */
public class BlockBox {
    public final int minX;
    public final int minY;
    public final int minZ;
    public final int maxX;
    public final int maxY;
    public final int maxZ;

    public BlockBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public BlockBox(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Extends this box's max position on each axis by the given amounts.
     * @param x number of blocks to extend on the X axis
     * @param y number of blocks to extend on the Y axis
     * @param z number of blocks to extend on the Z axis
     * @return extended copy of this box
     */
    public BlockBox extend(int x, int y, int z) {
        return new BlockBox(this.minX, this.minY, this.minZ, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    /**
     * Expands this box in both directions on each axis by the given amounts.
     * @param x number of blocks to expand on the X axis
     * @param y number of blocks to expand on the Y axis
     * @param z number of blocks to expand on the Z axis
     * @return expanded copy of this box
     */
    public BlockBox expand(int x, int y, int z) {
        int x1 = this.minX - x;
        int y1 = this.minY - y;
        int z1 = this.minZ - z;
        int x2 = this.maxX + x;
        int y2 = this.maxY + y;
        int z2 = this.maxZ + z;
        return new BlockBox(x1, y1, z1, x2, y2, z2);
    }

    /**
     * Expands this box by {@code amount} blocks in both direction on every axis.
     * @param amount number of blocks to expand
     * @return expanded copy of this box
     */
    public BlockBox expand(int amount) {
        return this.expand(amount, amount, amount);
    }

    public BlockBox offset(int x, int y, int z) {
        int x1 = this.minX + x;
        int y1 = this.minY + y;
        int z1 = this.minZ + z;
        int x2 = this.maxX + x;
        int y2 = this.maxY + y;
        int z2 = this.maxZ + z;
        return new BlockBox(x1, y1, z1, x2, y2, z2);
    }

    public BlockBox offset(BlockPos pos) {
        return this.offset(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockPos center() {
        return new BlockPos(this.minX + (this.maxX - this.minX + 1) / 2, this.minY + (this.maxY - this.minY + 1) / 2, this.minZ + (this.maxZ - this.minZ + 1) / 2);
    }

    public BlockBox up(int distance) {
        return new BlockBox(this.minX, this.minY + distance, this.minZ, this.maxX, this.maxY + distance, this.maxZ);
    }

    /**
     * @return copy of this box shifted up by 1 block.
     */
    public BlockBox up() {
        return this.up(1);
    }
}
