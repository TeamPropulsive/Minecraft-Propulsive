package io.github.teampropulsive.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

import static io.github.teampropulsive.block.Blocks.LAUNCH_PAD;
import static io.github.teampropulsive.block.Blocks.LAUNCH_TOWER;

public class BlueprintTable extends Block {
    public BlueprintTable(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        get_area(world, pos);

    }

    public void get_area(World world, BlockPos pos) {
        ArrayList<BlockPos> pad = new ArrayList<>();
        int max_y = pos.getY();
        max_y = get_adjacent_pads(world, pos, pad, max_y); // Returns all launch pad block positions and the max y
        int max_x = Integer.MIN_VALUE;
        int max_z = Integer.MIN_VALUE;
        int min_x = Integer.MAX_VALUE;
        int min_z = Integer.MAX_VALUE;
        for (BlockPos blockPos : pad) {
            if (blockPos.getX() > max_x)
                max_x = blockPos.getX();
            if (blockPos.getZ() > max_z)
                max_z = blockPos.getZ();
            if (blockPos.getX() < min_x)
                min_x = blockPos.getX();
            if (blockPos.getZ() < min_z)
                min_z = blockPos.getZ();
        }

        Vec3i point_a = new Vec3i(max_x, max_y, max_z);
        Vec3i point_b = new Vec3i(min_x, pos.getY(), min_z);
        System.out.println(pad);
        System.out.println(point_a);
        System.out.println(point_b);
    }

    public int get_adjacent_pads(World world, BlockPos pos, ArrayList<BlockPos> pad, int tower_y) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos p = pos.add(new Vec3i(x, pos.getY(), z));
                boolean exists = false;
                for (BlockPos blockPos : pad) {
                    if (blockPos == p) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    Block block = world.getBlockState(p).getBlock();
                    if (block == LAUNCH_PAD)  {
                        pad.add(p);
                        tower_y = get_adjacent_pads(world, p, pad, tower_y); // Update tower_y recursively
                    } else if (block == LAUNCH_TOWER) {
                        BlockPos height = p.up();
                        while (world.getBlockState(height).getBlock() == LAUNCH_TOWER) {
                            height = height.up();
                        }
                        if (height.getY() > tower_y) {
                            tower_y = height.getY();
                        }
                    }
                }

            }
        }
        return tower_y;
    }

}
