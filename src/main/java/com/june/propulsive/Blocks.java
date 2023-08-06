package com.june.propulsive;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Blocks {
//    public static final Block MOON_SURFACE = new Block(
//            FabricBlockSettings.create().requiresTool().strength(3.0f, 9.0f)
//    );

    public static void register() {
//        registerBlock(MOON_SURFACE, "moon_surface");
    }

    private static void registerBlock(Block block, String name) {
        Propulsive.LOGGER.info("Registering Blocks for Propulsive");
        Registry.register(Registries.BLOCK, Propulsive.id(name), block);
    }
}
