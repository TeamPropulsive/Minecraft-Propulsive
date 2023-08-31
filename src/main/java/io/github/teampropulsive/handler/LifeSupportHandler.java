package io.github.teampropulsive.handler;

import io.github.teampropulsive.Propulsive;
import net.minecraft.entity.Entity;

import static io.github.teampropulsive.Propulsive.TEST_ROCKET;

public class LifeSupportHandler {
    public static void LifeSupport(Entity entity) {
        if (entity.getWorld().getRegistryKey() == Propulsive.SPACE && entity.getType() != TEST_ROCKET) // TODO : Check for space suit + enough oxygen
            entity.damage(entity.getWorld().getDamageSources().create(Propulsive.OXYGEN_DAMAGE_TYPE), Float.MAX_VALUE);

    }
}
