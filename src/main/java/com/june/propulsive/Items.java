package com.june.propulsive;

import com.june.propulsive.armor.SpaceArmorMaterial;
import net.minecraft.item.ArmorItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {

    public static final ArmorItem SPACE_HELMET = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new net.minecraft.item.Item.Settings());
    public static final ArmorItem SPACE_CHESTPLATE = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new net.minecraft.item.Item.Settings());
    public static final ArmorItem SPACE_LEGGINGS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new net.minecraft.item.Item.Settings());
    public static final ArmorItem SPACE_BOOTS = new ArmorItem(SpaceArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new net.minecraft.item.Item.Settings());
    public static void register() {
        Propulsive.LOGGER.info("Registering Items for Propulsive");
        Registry.register(Registries.ITEM, new Identifier("propulsive", "space_helmet"), SPACE_HELMET);
        Registry.register(Registries.ITEM, new Identifier("propulsive", "space_chestplate"), SPACE_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier("propulsive", "space_leggings"), SPACE_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier("propulsive", "space_boots"), SPACE_BOOTS);
    }
}
