package io.github.teampropulsive;


import io.github.teampropulsive.armor.SpaceArmorMaterial;
import io.github.teampropulsive.block.Blocks;
import io.github.teampropulsive.types.GasCanister;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import static io.github.teampropulsive.util.Gases.*;

public class Items {
    public static final Item OXYGEN_TANK = new GasCanister(new FabricItemSettings(), OXYGEN, 1000);
    public static final Item OXYGEN_CANISTER = new GasCanister(new FabricItemSettings(), OXYGEN, 1000);
    public static final Item METHANE_CANISTER = new GasCanister(new FabricItemSettings(), METHANE, 1000);
    public static final Item HYDROGEN_CANISTER = new GasCanister(new FabricItemSettings(), HYDROGEN, 1000);
    public static final Item ALUMINUM_NUGGET = new Item(new FabricItemSettings());
    public static final Item RAW_ALUMINUM = new Item(new FabricItemSettings());
    public static final Item ALUMINUM_INGOT = new Item(new FabricItemSettings());
    public static final ArmorItem SPACE_HELMET = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem SPACE_CHESTPLATE = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem SPACE_LEGGINGS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final ArmorItem SPACE_BOOTS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new FabricItemSettings());
    private static final ItemGroup PROPULSIVE_ITEMS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(OXYGEN_TANK))
            .displayName(Text.translatable("itemGroup.propulsive.item"))
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, Propulsive.id("propulsive_items"), PROPULSIVE_ITEMS);
        // Canisters
        registerItem("oxygen_tank", OXYGEN_TANK, true);
        registerItem("oxygen_canister", OXYGEN_CANISTER, true);
        registerItem("methane_canister", METHANE_CANISTER, true);
        registerItem("hydrogen_canister", HYDROGEN_CANISTER, true);

        // Space suit
        registerItem("space_helmet", SPACE_HELMET, true);
        registerItem("space_chestplate", SPACE_CHESTPLATE, true);
        registerItem("space_leggings", SPACE_LEGGINGS, true);
        registerItem("space_boots", SPACE_BOOTS, true);

        // Moon blocks
        registerBlockItem("lunar_regolith", Blocks.MOON_REGOLITH, true);
        registerBlockItem("volcanic_lunar_regolith", Blocks.VOLCANIC_MOON_REGOLITH, true);
        registerBlockItem("anorthosite", Blocks.ANORTHOSITE, true);

        // Launch pad blocks
        registerBlockItem("launch_pad", Blocks.LAUNCH_PAD, true);
        registerBlockItem("launch_tower", Blocks.LAUNCH_TOWER, true);
        registerBlockItem("blueprint_table", Blocks.BLUEPRINT_TABLE, true);

        // Aluminum
        registerItem("raw_aluminum", RAW_ALUMINUM, true);
        registerItem("aluminum_ingot", ALUMINUM_INGOT, true);
        registerItem("aluminum_nugget", ALUMINUM_NUGGET, true);
        registerBlockItem("aluminum_block", Blocks.ALUMINUM_BLOCK, true);
        registerBlockItem("aluminum_ore_block", Blocks.ALUMINUM_ORE_BLOCK, true);
        registerBlockItem("aluminum_ore", Blocks.ALUMINUM_ORE, true);
        registerBlockItem("deepslate_aluminum_ore", Blocks.ALUMINUM_DEEPSLATE_ORE, true);
    }
    private static void registerItem(String path, Item item, boolean addToItemGroup) {
        Registry.register(Registries.ITEM, Propulsive.id(path), item);
        if (addToItemGroup) {
            ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, Propulsive.id("propulsive_items"))).register(content -> {
                content.add(item);
            });
        }

    }
    private static void registerBlockItem(String path, Block block,  boolean addToItemGroup) {
        BlockItem item = new BlockItem(block, new FabricItemSettings());
        registerItem(path, item, addToItemGroup);
    }
}
