package io.github.teampropulsive;


import io.github.teampropulsive.armor.SpaceArmorMaterial;
import io.github.teampropulsive.types.GasCanister;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {
    public static final Item OXYGEN_CANISTER = new GasCanister(new FabricItemSettings(), Propulsive.id("gas/oxygen"), 1000);
    public static final Item METHANE_CANISTER = new GasCanister(new FabricItemSettings(), Propulsive.id("gas/methane"), 1000);
    public static final Item HYDROGEN_CANISTER = new GasCanister(new FabricItemSettings(), Propulsive.id("gas/hydrogen"), 1000);
    public static final ArmorItem SPACE_HELMET = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem SPACE_CHESTPLATE = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem SPACE_LEGGINGS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final ArmorItem SPACE_BOOTS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new FabricItemSettings());
    public static void register() {
        registerItem("oxygen_canister", OXYGEN_CANISTER);
        registerItem("methane_canister", METHANE_CANISTER);
        registerItem("hydrogen_canister", HYDROGEN_CANISTER);

        registerItem("space_helmet", SPACE_HELMET);
        registerItem("space_chestplate", SPACE_CHESTPLATE);
        registerItem("space_leggings", SPACE_LEGGINGS);
        registerItem("space_boots", SPACE_BOOTS);

        registerBlockItem("lunar_regolith", Blocks.MOON_REGOLITH);
        registerBlockItem("volcanic_lunar_regolith", Blocks.VOLCANIC_MOON_REGOLITH);
        registerBlockItem("anorthosite", Blocks.ANORTHOSITE);

        registerBlockItem("aluminum_block", Blocks.ALUMINUM_BLOCK);
        registerBlockItem("aluminum_ore", Blocks.ALUMINUM_ORE);
        registerBlockItem("aluminum_deepslate_ore", Blocks.ALUMINUM_DEEPSLATE_ORE);
    }
    private static void registerItem(String path, Item item) {
        Registry.register(Registries.ITEM, Propulsive.id(path), item);
    }
    private static void registerBlockItem(String path, Block block) {
        Registry.register(Registries.ITEM, Propulsive.id(path), new BlockItem(block, new FabricItemSettings()));
    }
}
