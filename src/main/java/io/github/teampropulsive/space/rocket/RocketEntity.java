package io.github.teampropulsive.space.rocket;

import io.github.teampropulsive.space.spacecraft.SpacecraftEntity;
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

public class RocketEntity extends SpacecraftEntity {

    public boolean hasWarpAbility = false;
    public Vec3d velocity;
    public Vec3d dockingPosition;
    public float storedOxygen = 0;
    public float maxOxygen = 1000; // We can settle on a number here later
    public RocketEntity(EntityType<? extends SpacecraftEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canJump() {
        return hasWarpAbility;
    }

    @Override
    protected void jump(float strength, Vec3d movementInput) {
        //super.jump(strength, movementInput);
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
}