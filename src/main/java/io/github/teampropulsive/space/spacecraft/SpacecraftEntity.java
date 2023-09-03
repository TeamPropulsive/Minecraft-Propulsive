package io.github.teampropulsive.space.spacecraft;

import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.keybind.ShipThrottleDownKeybind;
import io.github.teampropulsive.keybind.ShipThrottleUpKeybind;
import io.github.teampropulsive.space.rocket.RocketEntity;
import io.github.teampropulsive.types.Planet;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static io.github.teampropulsive.Propulsive.*;
import static io.github.teampropulsive.keybind.DockShipKeybind.dockShipKey;

public class SpacecraftEntity extends AbstractHorseEntity { // TODO: Make not horse https://github.com/Terra-Studios/WackyVessels/blob/master/src/main/java/dev/sebastianb/wackyvessels/entity/SitEntity.java

    public float throttle = 0.0F;
    public float throttle_sensitivity = 0.05F;
    public int seatCount = 1;
    public boolean canMove = true;
    public ArrayList<Vec3d> playerPositionOffsets = new ArrayList<>();
    public ArrayList<Float> playerYawOffsets = new ArrayList<>();
    public ArrayList<Vec3d> dockingPortPositions = new ArrayList<>();
    public ArrayList<SpacecraftEntity> dockedCraft = new ArrayList<>();
    public Vec3d velocity = Vec3d.ZERO;
    public float storedOxygen = 0;
    public float maxOxygen = 1000; // We can settle on a number here later

    protected SpacecraftEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);

        for (int i = 0; i < seatCount; i++) {
            playerPositionOffsets.add(new Vec3d(0.0, 0.0, 0.0));
            playerYawOffsets.add(0.0F);
        }
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dockShipKey.wasPressed())
                onDockingTrigger();
        });

        // Ship throttle

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (this.canMove)
                while (ShipThrottleUpKeybind.shipThrottleUpKeybind.isPressed()) throttle_up();
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (this.canMove)
                while (ShipThrottleDownKeybind.shipThrottleDownKeybind.isPressed()) throttle_down();
        });
    }


    public void throttle_up() {
        if (throttle < 1.0f)
            throttle += throttle_sensitivity;
        this.jumpStrength = throttle;
    }
    public void throttle_down() {
        if (throttle > 0.0f)
            throttle -= throttle_sensitivity;
        this.jumpStrength = throttle;
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
    public SpacecraftEntity getClosestSpacecraft() {
        List<RocketEntity> craft = this.getWorld().getEntitiesByType(
                Propulsive.TEST_ROCKET,
                new Box(
                        this.getPos().subtract(new Vec3d(10.0, 10.0, 10.0)),
                        this.getPos().subtract(new Vec3d(-10.0, -10.0, -10.0))
                ),
                Entity::isAlive
        );
        SpacecraftEntity target = null;
        for (SpacecraftEntity entity : craft) {
            if (!entity.dockingPortPositions.isEmpty())
                target = entity;
            if (target != null)
                break;
        }
        return target;
    }
    public Vec3d getClosestPort(SpacecraftEntity target) {
        Vec3d sourcePos = this.getPos();
        List<Vec3d> dockingPorts = this.dockingPortPositions;
        Vec3d closestPort = null;
        double closestDistance = Double.MAX_VALUE;
        for (Vec3d portPosition : dockingPorts) {
            Vec3d portWorldPos = portPosition.add(sourcePos);
            double distance = portWorldPos.distanceTo(target.getPos());
            if (distance < closestDistance) {
                closestPort = portWorldPos;
                closestDistance = distance;
            }
        }
        return closestPort;
    }
    public void applyRotation(SpacecraftEntity target) {
        Vec3d sourceCenter = this.getPos();
        Vec3d targetCenter = target.getPos();
        Vec3d sourceToTarget = targetCenter.subtract(sourceCenter).normalize();
        double yaw = Math.atan2(sourceToTarget.z, sourceToTarget.x);
        double pitch = Math.asin(-sourceToTarget.y);
        this.setYaw((float) yaw);
        this.setPitch((float) pitch);
    }
    public void onDockingTrigger() {
        SpacecraftEntity target = getClosestSpacecraft();
        System.out.println(target);
        if (target != null) {
            Vec3d sourcePortOffset = new Vec3d(0.0, 1.0, 0.0);
            Vec3d targetPortOffset = new Vec3d(0.0, -1.0, 0.0);
            Vec3d sourcePort = this.getClosestPort(target).add(sourcePortOffset);
            Vec3d targetPort = target.getClosestPort(this).add(targetPortOffset);
            this.applyRotation(target);
            double dockingDistanceThreshold = 0.1;
            while (sourcePort.distanceTo(targetPort) > dockingDistanceThreshold) {
                Vec3d direction = targetPort.subtract(sourcePort).normalize();
                double speed = 1;
                this.move(direction.multiply(speed));
                target.move(direction.multiply(-speed));
                sourcePort = this.getClosestPort(target).add(sourcePortOffset);
                targetPort = target.getClosestPort(this).add(targetPortOffset);
            }
            this.dockedCraft.add(target);
            target.dockedCraft.add(this);
        }
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
        this.moveWithModules(this.velocity);
    }

    @Override
    public boolean isOnGround() { // it's on the ground I swear!
        return true;
    }

    @Override
    public boolean isTame() {
        return true;
    }

    public void move(Vec3d offset) {
        Vec3d p = this.getPos().add(offset);
        this.setPos(p.x, p.y, p.z);
    }
    public void moveWithModules(Vec3d offset) {
        this.move(offset);
        ArrayList<SpacecraftEntity> done = new ArrayList<>();
        done.add(this);
        moveWithModulesIter(offset, done);
    }

    private void moveWithModulesIter(Vec3d offset, ArrayList<SpacecraftEntity> done) {
        this.move(offset);
        for (SpacecraftEntity craft : this.dockedCraft) { // Could maybe be optimised later? Could be a little intensive with larger ships
            if (!done.contains(craft)) {
                craft.moveWithModulesIter(offset, done);
            }
        }
        done.add(this);
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