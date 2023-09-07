package io.github.teampropulsive.block;

import io.github.teampropulsive.Propulsive;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Blocks {

    public static final Block MOON_REGOLITH = new Block(
           FabricBlockSettings.create().requiresTool().strength(3.0f, 9.0f)
    );
    public static final Block VOLCANIC_MOON_REGOLITH = new Block(
            FabricBlockSettings.create().requiresTool().strength(4.0f, 10.0f)
    );
    public static final Block BAUXITE = new Block(
            FabricBlockSettings.create().requiresTool().strength(1.5f, 6.0f).requiresTool()
    );
    public static final Block PURE_BAUXITE = new Block(
            FabricBlockSettings.create().requiresTool().strength(1.5f, 6.0f).requiresTool()
    );
    public static final Block ALUMINUM_BLOCK = new Block(
            FabricBlockSettings.create().requiresTool().strength(5.0f, 6.0f).requiresTool()
    );
    public static final Block RAW_BAUXITE_BLOCK = new Block(
            FabricBlockSettings.create().requiresTool().strength(5.0f, 6.0f).requiresTool()
    );
    public static final Block ANORTHOSITE = new Block(
            FabricBlockSettings.create().requiresTool().strength(5.0f, 11.0f)
    );

    public static final Block LAUNCH_PAD = new Block(
            FabricBlockSettings.create().requiresTool().strength(5.0f, 11.0f)
    );
    public static final Block LAUNCH_TOWER = new Block(
            FabricBlockSettings.create().requiresTool().strength(5.0f, 11.0f)
    );
    public static final Block BLUEPRINT_TABLE = new BlueprintTable(
            FabricBlockSettings.create().requiresTool().strength(5.0f, 11.0f)
    );
    public static final BlockEntityType<BlueprintTableBlockEntity> BLUEPRINT_TABLE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Propulsive.id("blueprint_table"),
            FabricBlockEntityTypeBuilder.create(BlueprintTableBlockEntity::new, BLUEPRINT_TABLE).build()
    );

    public static void register() {
        registerBlock(MOON_REGOLITH, "lunar_regolith");
        registerBlock(VOLCANIC_MOON_REGOLITH, "volcanic_lunar_regolith");
        registerBlock(ANORTHOSITE, "anorthosite");

        registerBlock(LAUNCH_PAD, "launch_pad");
        registerBlock(LAUNCH_TOWER, "launch_tower");
        registerBlock(BLUEPRINT_TABLE, "blueprint_table");

        registerBlock(PURE_BAUXITE, "pure_bauxite");
        registerBlock(BAUXITE, "bauxite");
        registerBlock(ALUMINUM_BLOCK, "aluminum_block");
        registerBlock(RAW_BAUXITE_BLOCK, "raw_bauxite_block");
    }

    private static void registerBlock(Block block, String name) {
        Registry.register(Registries.BLOCK, Propulsive.id(name), block);
    }
}
