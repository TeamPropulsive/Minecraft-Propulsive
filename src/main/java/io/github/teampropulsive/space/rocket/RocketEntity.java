package io.github.teampropulsive.space.rocket;

import io.github.teampropulsive.types.Planet;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.joml.Vector3f;

import static io.github.teampropulsive.Propulsive.SPACE;
import static io.github.teampropulsive.Propulsive.TICKABLE_PLANETS;

public class RocketEntity extends AbstractHorseEntity {

    public boolean hasWarpAbility = false;
    public Vec3d velocity;
    public float storedOxygen = 0;

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
        Vec3d a = controllingPlayer.getRotationVecClient();
        float x = (float) a.x;
        float y = (float) a.y;
        float z = (float) a.z;
        return new Vec3d(x, y, z);
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
        return hasWarpAbility;
    }

    @Override
    protected void jump(float strength, Vec3d movementInput) {
        //super.jump(strength, movementInput);
    }


    protected Vec3d calculateGravity() {
        Vec3d velocity = Vec3d.ZERO;
        if (this.getWorld().getRegistryKey() == SPACE) {
            for (Planet planet : TICKABLE_PLANETS) {
                Vec3d distanceDirection = planet.currentPos.subtract(this.getPos());
                velocity.add(
                        new Vec3d(
                                distanceDirection.x * planet.planetSize,
                                distanceDirection.y * planet.planetSize,
                                distanceDirection.z * planet.planetSize
                        )
                );
            }
        }
        return velocity;
    }
}