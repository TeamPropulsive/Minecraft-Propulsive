package com.june.propulsive;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static final ItemGroup PROPULSIVE_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier("propulsive", "bliffer_seed"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.propulsive.propulsive_group"))
                    .icon(() -> new ItemStack(Items.SPACE_HELMET)).entries((displayContext, entries) -> {
                        entries.add(Items.SPACE_HELMET);
                        entries.add(Items.SPACE_CHESTPLATE);
                        entries.add(Items.SPACE_LEGGINGS);
                        entries.add(Items.SPACE_BOOTS);
                    }).build());
    public static void register() {
        Propulsive.LOGGER.info("Registering Item Groups for Propulsive");
    }
}
