package io.github.teampropulsive.space.station;

import io.github.teampropulsive.space.spacecraft.SpacecraftEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class StationEntity extends SpacecraftEntity {
    public Identifier variation;
    protected StationEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }
}
