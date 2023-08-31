package io.github.teampropulsive.space.spacecraft;

import io.github.teampropulsive.types.Planet;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;

import static io.github.teampropulsive.Propulsive.SPACE;
import static io.github.teampropulsive.Propulsive.TICKABLE_PLANETS;

public class SpacecraftEntity extends AbstractHorseEntity {

    protected SpacecraftEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
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
    public boolean isTame() {
        return true;
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