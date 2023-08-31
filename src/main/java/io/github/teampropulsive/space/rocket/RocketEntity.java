package io.github.teampropulsive.space.rocket;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;

public class RocketEntity extends AbstractHorseEntity {
    public RocketEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super((EntityType<? extends AbstractHorseEntity>) entityType, world);
    }
    @Override
    public boolean canBeSaddled() {
        return false;
    }
    @Override
    public boolean isSaddled() {
        return true;
    }
    @Override
    public EntityView method_48926() {
        return null;
    }

    @Override
    public boolean isAiDisabled() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
    }
    @Override
    public void tickMovement() {
        super.tickMovement();
    }
    @Override
    public void tickRiding() {
        super.tickRiding();
    }

    @Override
    public boolean isTame() {
        return true;
    }

    @Override
    public boolean canJump() {
        return false;
    }
}