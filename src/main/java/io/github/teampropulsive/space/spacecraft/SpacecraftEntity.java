package io.github.teampropulsive.space.spacecraft;

import io.github.teampropulsive.types.Planet;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;

import java.util.ArrayList;

import static io.github.teampropulsive.Propulsive.SPACE;
import static io.github.teampropulsive.Propulsive.TICKABLE_PLANETS;
import static io.github.teampropulsive.keybind.DockShipKeybind.dockShipKey;

public class SpacecraftEntity extends AbstractHorseEntity {

    public int seatCount = 1;
    public ArrayList<Vec3d> playerPositionOffsets = new ArrayList<>();
    public ArrayList<Float> playerYawOffsets = new ArrayList<>();
    public Vec3d dockingPosition;
    public Vec3d velocity;
    public float storedOxygen = 0;
    public float maxOxygen = 1000; // We can settle on a number here later

    protected SpacecraftEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
        for (int i = 0; i < seatCount; i++) {
            playerPositionOffsets.add(new Vec3d(0.0, 0.0, 0.0));
            playerYawOffsets.add(0.0F);
        }
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dockShipKey.wasPressed()) {
                onDockingTrigger();
            }
        });
    }
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.getPassengerList().size() < seatCount && !this.isBaby()) {
            this.putPlayerOnBack(player);
        }
        return ActionResult.success(this.getWorld().isClient);
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
        int i = this.getPassengerList().indexOf(passenger);
        passenger.setPosition(this.getPos().add(this.playerPositionOffsets.get(i)));
        passenger.setYaw((this.getYaw() + this.playerYawOffsets.get(i)));
        positionUpdater.accept(passenger, passenger.getX(), passenger.getY(), passenger.getZ());
    }


    public void onDockingTrigger() {
        System.out.println("AAAAAAAAAAAAAAAAAAAaa");
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
        return super.getWorld();
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

    protected Vec3d calculateGravity() { // This probably works idk
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