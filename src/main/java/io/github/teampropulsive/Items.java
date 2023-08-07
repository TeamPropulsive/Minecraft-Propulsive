package io.github.teampropulsive;

import io.github.teampropulsive.armor.SpaceArmorMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {
    public static final ArmorItem SPACE_HELMET = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new net.minecraft.item.Item.Settings());
    public static final ArmorItem SPACE_CHESTPLATE = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new net.minecraft.item.Item.Settings());
    public static final ArmorItem SPACE_LEGGINGS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new net.minecraft.item.Item.Settings());
    public static final ArmorItem SPACE_BOOTS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new net.minecraft.item.Item.Settings());
    public static void register() {
        Registry.register(Registries.ITEM, new Identifier("propulsive", "space_helmet"), SPACE_HELMET);
        Registry.register(Registries.ITEM, new Identifier("propulsive", "space_chestplate"), SPACE_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier("propulsive", "space_leggings"), SPACE_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier("propulsive", "space_boots"), SPACE_BOOTS);

        Registry.register(Registries.ITEM, new Identifier("propulsive", "lunar_regolith"), new BlockItem(Blocks.MOON_REGOLITH, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("propulsive", "volcanic_lunar_regolith"), new BlockItem(Blocks.VOLCANIC_MOON_REGOLITH, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("propulsive", "anorthosite"), new BlockItem(Blocks.ANORTHOSITE, new FabricItemSettings()));
    }
}
