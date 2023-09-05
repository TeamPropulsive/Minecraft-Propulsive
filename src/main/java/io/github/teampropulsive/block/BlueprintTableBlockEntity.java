package io.github.teampropulsive.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.teampropulsive.block.Blocks.*;
import static io.github.teampropulsive.block.Blocks.LAUNCH_PAD;

public class BlueprintTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    public int tower_size = 0;
    public int pad_size = 0;
    public boolean show_bounding_box = false;
    public BlueprintTableBlockEntity(BlockPos pos, BlockState state) {
        super(BLUEPRINT_TABLE_BLOCK_ENTITY, pos, state);
    }


    public void update_area(World world, BlockPos pos) {
        int max_pad_size = 10;
        int max_tower_size = 10;
        BlockPos pad_offset = get_pad_offset(world, pos);
        if (pad_offset != null) {
            this.pad_size = get_pad_size(max_pad_size, world, pos, pad_offset);
            this.tower_size = get_tower_size(this.pad_size, max_tower_size, world, pos, pad_offset);
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
    public BlockPos get_pad_offset(World world, BlockPos pos) {
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

    @Override
    public Text getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }
}

