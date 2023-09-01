package io.github.teampropulsive.space.station;

import io.github.teampropulsive.space.spacecraft.SpacecraftEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qouteall.q_misc_util.my_util.Vec2d;

public class StationEntity extends SpacecraftEntity {
    public Identifier variation;
    protected StationEntity(EntityType<? extends SpacecraftEntity> entityType, World world) {
        super(entityType, world);
    }
}
