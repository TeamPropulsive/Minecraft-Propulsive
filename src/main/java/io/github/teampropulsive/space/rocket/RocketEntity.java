package io.github.teampropulsive.space.rocket;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class RocketEntity extends PathAwareEntity {
    public RocketEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}