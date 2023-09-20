package io.github.teampropulsive;


import io.github.teampropulsive.armor.SpaceArmorMaterial;
import io.github.teampropulsive.block.Blocks;
import io.github.teampropulsive.types.GasCanister;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
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
    public static final Item RAW_BAUXITE = new Item(new FabricItemSettings());
    public static final Item ALUMINUM_INGOT = new Item(new FabricItemSettings());
    public static final Item TITANIUM_INGOT = new Item(new FabricItemSettings());
    public static final Item TITANIUM_NUGGET = new Item(new FabricItemSettings());
    public static final Item RAW_TITANIUM = new Item(new FabricItemSettings());
    public static final ArmorItem SPACE_HELMET = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem SPACE_CHESTPLATE = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem SPACE_LEGGINGS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final ArmorItem SPACE_BOOTS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new FabricItemSettings());
    private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Propulsive.id("items"));

    public static void register() {
        Item[] items = {
            // Canisters
            registerItem("oxygen_tank", OXYGEN_TANK),
            registerItem("oxygen_canister", OXYGEN_CANISTER),
            registerItem("methane_canister", METHANE_CANISTER),
            registerItem("hydrogen_canister", HYDROGEN_CANISTER),

            // Space suit
            registerItem("space_helmet", SPACE_HELMET),
            registerItem("space_chestplate", SPACE_CHESTPLATE),
            registerItem("space_leggings", SPACE_LEGGINGS),
            registerItem("space_boots", SPACE_BOOTS),

            // Moon blocks
            registerBlockItem("lunar_regolith", Blocks.MOON_REGOLITH),
            registerBlockItem("volcanic_lunar_regolith", Blocks.VOLCANIC_MOON_REGOLITH),
            registerBlockItem("anorthosite", Blocks.ANORTHOSITE),

            // Launch pad blocks
            registerBlockItem("launch_pad", Blocks.LAUNCH_PAD),
            registerBlockItem("launch_tower", Blocks.LAUNCH_TOWER),
            registerBlockItem("blueprint_table", Blocks.BLUEPRINT_TABLE),

            // Aluminum
            registerItem("raw_bauxite", RAW_BAUXITE),
            registerItem("aluminum_ingot", ALUMINUM_INGOT),
            registerItem("aluminum_nugget", ALUMINUM_NUGGET),
            registerBlockItem("pure_bauxite", Blocks.PURE_BAUXITE),
            registerBlockItem("bauxite", Blocks.BAUXITE),
            registerBlockItem("aluminum_block", Blocks.ALUMINUM_BLOCK),
            registerBlockItem("raw_bauxite_block", Blocks.RAW_BAUXITE_BLOCK),

            // Titanium
            registerItem("raw_titanium", RAW_TITANIUM),
            registerItem("titanium_ingot", TITANIUM_INGOT),
            registerItem("titanium_nugget", TITANIUM_NUGGET),
            registerBlockItem("raw_titanium_block", Blocks.RAW_TITANIUM_BLOCK),
            registerBlockItem("titanium_block", Blocks.TITANIUM_BLOCK),
            registerBlockItem("titanium_ore", Blocks.TITANIUM_ORE),
        };

        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP,
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(OXYGEN_TANK))
                        .displayName(Text.translatable("itemGroup.propulsive.items"))
                        .entries(((displayContext, entries) -> {
                            for (Item item : items)
                                entries.add(item);
                        })).build());
    }

    public static Item registerItem(String path, Item item) {
        return Registry.register(Registries.ITEM, Propulsive.id(path), item);
    }

    private static Item registerBlockItem(String path, Block block) {
        BlockItem item = new BlockItem(block, new FabricItemSettings());
        return registerItem(path, item);
    }
}
