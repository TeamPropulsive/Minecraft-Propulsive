package io.github.teampropulsive.space.rocket;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
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
    public boolean isOnGround() { // it's on the ground I swear!
        return true;
    }
    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        this.setInAir(false);
        float f = controllingPlayer.sidewaysSpeed * 0.5f;
        float g = controllingPlayer.forwardSpeed;
        if (g <= 0.0f) {
            g *= 0.25f;
        }
        return new Vec3d(0.0, 1.0, 0.0);
    }
    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
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