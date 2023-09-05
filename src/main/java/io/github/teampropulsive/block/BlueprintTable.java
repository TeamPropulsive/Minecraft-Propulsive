package io.github.teampropulsive.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.teampropulsive.block.Blocks.LAUNCH_PAD;
import static io.github.teampropulsive.block.Blocks.LAUNCH_TOWER;

public class BlueprintTable extends Block {
    public BlueprintTable(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        get_area(world, pos);
        super.onPlaced(world, pos, state, placer, itemStack);

    }

    public void get_area(World world, BlockPos pos) {
        int max_pad_size = 10;
        int max_tower_size = 10;
        BlockPos pad_offset = get_pad_offset(world, pos);
        if (pad_offset != null) {
            int pad_scale = get_pad_size(max_pad_size, world, pos, pad_offset);
            int tower_height = get_tower_size(pad_scale, max_tower_size, world, pos, pad_offset);
            System.out.println(pad_scale);
            System.out.println(tower_height);
        }

    }


    private int get_tower_size(int pad_size, int max, World world, BlockPos pos, BlockPos offset) {
        BlockPos tower_pos = find_tower(
                pad_size+1,
                world,
                pos.add(offset.multiply(pad_size+2))
        );
        if (tower_pos != null) {
            for (int y = 0; y <= max; y++) {
                if (!(world.getBlockState(tower_pos.add(new Vec3i(0, y, 0))).getBlock() == LAUNCH_TOWER))
                    return y-1;
            }
        }
        return 0;
    } // Gets the height of the launch tower
    private BlockPos find_tower(int size, World world, BlockPos pos) {
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                System.out.println(pos.add(new Vec3i(x, 0, z)));
                System.out.println(world.getBlockState(pos.add(new Vec3i(x, 0, z))).getBlock());
                if (world.getBlockState(pos.add(new Vec3i(x, 0, z))).getBlock() == LAUNCH_TOWER)
                    return pos.add(new Vec3i(x, 0, z));
            }
        }
        return null;
    } // Finds the base position of the launch tower
    private boolean check_square(int size, World world, BlockPos pos) {
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                if (!(world.getBlockState(pos.add(new Vec3i(x, 0, z))).getBlock() == LAUNCH_PAD))
                    return false;
            }
        }
        return true;
    } // Checks if a given square is a valid pad base
    private int get_pad_size(int max, World world, BlockPos pos, BlockPos offset) {
        for (int scale = 1; scale <= max; scale++) {
            boolean fits = check_square(
                    scale,
                    world,
                    pos.add(offset.multiply(scale+1))
            );
            if (!fits)
                return scale - 1;
        }
        return max;
    } // Gets the size of the launch pad base
    private BlockPos get_pad_offset(World world, BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if ((x == -1 && z == -1) || (x == 1 && z == 1) || (x == 1 && z == -1) || (x == -1 && z == 1))
                    continue;
                if (world.getBlockState(pos.add(new Vec3i(x, 0, z))).getBlock() == LAUNCH_PAD)
                    return new BlockPos(x, 0, z);
            }
        }
        return null;
    } // Gets the relative position of the pad base's center

}
