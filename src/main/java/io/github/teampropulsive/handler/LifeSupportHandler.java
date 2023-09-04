package io.github.teampropulsive.handler;

import io.github.teampropulsive.Propulsive;
import net.minecraft.entity.Entity;


public class LifeSupportHandler {
    public static void LifeSupport(Entity entity) {
        if (entity.getWorld().getRegistryKey() == Propulsive.SPACE) // TODO : Check for space suit + enough oxygen
            entity.damage(entity.getWorld().getDamageSources().create(Propulsive.OXYGEN_DAMAGE_TYPE), Float.MAX_VALUE);

    }
}
