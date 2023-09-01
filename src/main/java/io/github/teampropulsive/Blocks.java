package io.github.teampropulsive;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Blocks {
    public static final Block MOON_REGOLITH = new Block(
           FabricBlockSettings.create().requiresTool().strength(3.0f, 9.0f)
    );
    public static final Block VOLCANIC_MOON_REGOLITH = new Block(
            FabricBlockSettings.create().requiresTool().strength(4.0f, 10.0f)
    );
    public static final Block ALUMINUM_DEEPSLATE_ORE = new Block(
            FabricBlockSettings.create().requiresTool().strength(4.5f, 3.0f).requiresTool()
    );
    public static final Block ALUMINUM_ORE = new Block(
            FabricBlockSettings.create().requiresTool().strength(1.5f, 6.0f).requiresTool()
    );
    public static final Block ALUMINUM_BLOCK = new Block(
            FabricBlockSettings.create().requiresTool().strength(5.0f, 6.0f).requiresTool()
    );
    public static final Block ANORTHOSITE = new Block(
            FabricBlockSettings.create().requiresTool().strength(5.0f, 11.0f)
    );
    public static void register() {
        registerBlock(MOON_REGOLITH, "lunar_regolith");
        registerBlock(VOLCANIC_MOON_REGOLITH, "volcanic_lunar_regolith");
        registerBlock(ANORTHOSITE, "anorthosite");

        registerBlock(ALUMINUM_BLOCK, "lunar_regolith");
        registerBlock(ALUMINUM_ORE, "lunar_regolith");
        registerBlock(ALUMINUM_DEEPSLATE_ORE, "lunar_regolith");
    }

    private static void registerBlock(Block block, String name) {
        Registry.register(Registries.BLOCK, Propulsive.id(name), block);
    }
}
