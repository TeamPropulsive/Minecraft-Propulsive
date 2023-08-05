package com.june.propulsive;

public class Block {
    public static final Block MOON_SURFACE = registerBlock("moon_surface",
            new Block(FabricBlockSettings.copyOf(Blocks.END_STONE).sounds(BlockSoundGroup.END_STONE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(propulsive, name), block);
    }
}
