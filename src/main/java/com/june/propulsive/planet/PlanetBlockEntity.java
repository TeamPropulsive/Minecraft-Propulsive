package com.june.propulsive.planet;

import com.june.propulsive.Propulsive;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PlanetBlockEntity extends BlockEntity {
    // make sure the constructor's parameters are correct!
    public PlanetBlockEntity(BlockPos pos, BlockState state) {
        // We will create this BlockEntityType later on
        super(Propulsive.PLANET_BLOCK_ENTITY_TYPE, pos, state);
    }
}
