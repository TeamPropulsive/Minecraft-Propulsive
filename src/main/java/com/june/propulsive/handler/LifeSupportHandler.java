package com.june.propulsive.handler;

import com.june.propulsive.Propulsive;
import net.minecraft.entity.Entity;

import static com.june.propulsive.Propulsive.SPACE;

public class LifeSupportHandler {
    public static void LifeSupport(Entity entity) {

        if (entity.getWorld().getRegistryKey() == SPACE) // TODO : Check for space suit + enough oxygen
            entity.damage(entity.getWorld().getDamageSources().create(Propulsive.OXYGEN_DAMAGE_TYPE), Float.MAX_VALUE);

    }
}
