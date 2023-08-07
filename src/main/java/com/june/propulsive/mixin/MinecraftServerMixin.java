package com.june.propulsive.mixin;

import com.june.propulsive.Propulsive;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.api.PortalAPI;
import qouteall.imm_ptl.core.portal.Portal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.june.propulsive.Propulsive.OVERWORLD_HEIGHT;
import static com.june.propulsive.Propulsive.WORLD_SIZE;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    // small number to prevent zfighting at portal boundaries
    @Unique private static final double EPSILON = 0.05;

    // used to offset the portals so the faces of the world aren't all the same
    @Unique private static final Map<RegistryKey<World>, Integer> worldIdMap = Map.of(
            Propulsive.DIM_OW_FRONT, 0,
            Propulsive.DIM_OW_TOP, 1,
            Propulsive.DIM_OW_BOTTOM, 2,
            Propulsive.DIM_OW_LEFT, 3,
            Propulsive.DIM_OW_RIGHT, 4,
            Propulsive.DIM_OW_BACK, 5
    );

    @Shadow @Final private Map<RegistryKey<World>, ServerWorld> worlds;

    @Inject(method = "createWorlds", at = @At("TAIL"))
    public void createPortalsOnWorldCreation(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> overworldPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), Propulsive.DIM_OW_LEFT, new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(0, 0, 1), Propulsive.DIM_OW_TOP, new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(1, 0, 0), Propulsive.DIM_OW_RIGHT, new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(0, 0, -1), Propulsive.DIM_OW_BOTTOM, new Vec3d(0, 0, 1))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> leftPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), Propulsive.DIM_OW_BACK, new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(0, 0, 1), Propulsive.DIM_OW_TOP, new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(1, 0, 0), Propulsive.DIM_OW_FRONT, new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(0, 0, -1), Propulsive.DIM_OW_BOTTOM, new Vec3d(-1, 0, 0))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> rightPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), Propulsive.DIM_OW_FRONT, new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(0, 0, 1), Propulsive.DIM_OW_TOP, new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(1, 0, 0), Propulsive.DIM_OW_BACK, new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(0, 0, -1), Propulsive.DIM_OW_BOTTOM, new Vec3d(1, 0, 0))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> topPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), Propulsive.DIM_OW_LEFT, new Vec3d(0, 0, 1)),
                Triple.of(new Vec3d(0, 0, 1), Propulsive.DIM_OW_BACK, new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(1, 0, 0), Propulsive.DIM_OW_RIGHT, new Vec3d(0, 0, 1)),
                Triple.of(new Vec3d(0, 0, -1), Propulsive.DIM_OW_FRONT, new Vec3d(0, 0, 1))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> bottomPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), Propulsive.DIM_OW_LEFT, new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(0, 0, 1), Propulsive.DIM_OW_FRONT, new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(1, 0, 0), Propulsive.DIM_OW_RIGHT, new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(0, 0, -1), Propulsive.DIM_OW_BACK   , new Vec3d(0, 0, 1))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> backPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), Propulsive.DIM_OW_LEFT, new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(0, 0, 1), Propulsive.DIM_OW_BOTTOM, new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(1, 0, 0), Propulsive.DIM_OW_RIGHT, new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(0, 0, -1), Propulsive.DIM_OW_TOP, new Vec3d(0, 0, 1))
        );

        HashMap<RegistryKey<World>, List<Triple<Vec3d, RegistryKey<World>, Vec3d>>> portalsMap = new HashMap<>();
        portalsMap.put(Propulsive.DIM_OW_FRONT, overworldPortals);
        portalsMap.put(Propulsive.DIM_OW_LEFT, leftPortals);
        portalsMap.put(Propulsive.DIM_OW_RIGHT, rightPortals);
        portalsMap.put(Propulsive.DIM_OW_TOP, topPortals);
        portalsMap.put(Propulsive.DIM_OW_BOTTOM, bottomPortals);
        portalsMap.put(Propulsive.DIM_OW_BACK, backPortals);

        portalsMap.forEach((world, portals) -> portals.forEach(triple -> {
            Portal portal = Portal.entityType.create(this.worlds.get(world));
            portal.setOriginPos(
                    triple.getLeft()
                            .multiply(WORLD_SIZE + EPSILON)
                            .add(worldIdMap.get(world) * WORLD_SIZE, 0, 0)
            );
            portal.setDestinationDimension(triple.getMiddle());
            portal.setDestination(
                    triple.getRight()
                            .multiply(WORLD_SIZE - EPSILON)
                            .add(worldIdMap.get(triple.getMiddle()) * WORLD_SIZE, 0, 0));
            portal.setOrientationAndSize(
                    new Vec3d(triple.getLeft().z, 0, triple.getLeft().x),
                    new Vec3d(0, triple.getLeft().x != 0 ? 1 : -1, 0),
                    WORLD_SIZE * 2,
                    OVERWORLD_HEIGHT * 2
            );
            PortalAPI.addGlobalPortal(this.worlds.get(world), portal);
        }));
    }
}
