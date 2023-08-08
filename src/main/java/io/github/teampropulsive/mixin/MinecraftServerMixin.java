package io.github.teampropulsive.mixin;

import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.types.Planet;
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

import static io.github.teampropulsive.Propulsive.*;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    // small number to prevent zfighting at portal boundaries
    @Unique private static final double EPSILON = 0.05;
    
    @Shadow @Final private Map<RegistryKey<World>, ServerWorld> worlds;

    @Inject(method = "createWorlds", at = @At("TAIL"))
    public void createPortalsOnWorldCreation(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        // FIXME make this use PlanetDimensions.getCubeMap()
        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> overworldPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), EARTH_DIMENSIONS.left(), new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(0, 0, 1), EARTH_DIMENSIONS.top(), new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(1, 0, 0), EARTH_DIMENSIONS.right(), new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(0, 0, -1), EARTH_DIMENSIONS.bottom(), new Vec3d(0, 0, 1))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> leftPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), EARTH_DIMENSIONS.back(), new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(0, 0, 1), EARTH_DIMENSIONS.top(), new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(1, 0, 0), EARTH_DIMENSIONS.front(), new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(0, 0, -1), EARTH_DIMENSIONS.bottom(), new Vec3d(-1, 0, 0))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> rightPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), EARTH_DIMENSIONS.front(), new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(0, 0, 1), EARTH_DIMENSIONS.top(), new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(1, 0, 0), EARTH_DIMENSIONS.back(), new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(0, 0, -1), EARTH_DIMENSIONS.bottom(), new Vec3d(1, 0, 0))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> topPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), EARTH_DIMENSIONS.left(), new Vec3d(0, 0, 1)),
                Triple.of(new Vec3d(0, 0, 1), EARTH_DIMENSIONS.back(), new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(1, 0, 0), EARTH_DIMENSIONS.right(), new Vec3d(0, 0, 1)),
                Triple.of(new Vec3d(0, 0, -1), EARTH_DIMENSIONS.front(), new Vec3d(0, 0, 1))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> bottomPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), EARTH_DIMENSIONS.left(), new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(0, 0, 1), EARTH_DIMENSIONS.front(), new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(1, 0, 0), EARTH_DIMENSIONS.right(), new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(0, 0, -1), EARTH_DIMENSIONS.back(), new Vec3d(0, 0, 1))
        );

        List<Triple<Vec3d, RegistryKey<World>, Vec3d>> backPortals = List.of(
                Triple.of(new Vec3d(-1, 0, 0), EARTH_DIMENSIONS.left(), new Vec3d(-1, 0, 0)),
                Triple.of(new Vec3d(0, 0, 1), EARTH_DIMENSIONS.bottom(), new Vec3d(0, 0, -1)),
                Triple.of(new Vec3d(1, 0, 0), EARTH_DIMENSIONS.right(), new Vec3d(1, 0, 0)),
                Triple.of(new Vec3d(0, 0, -1), EARTH_DIMENSIONS.top(), new Vec3d(0, 0, 1))
        );

        HashMap<RegistryKey<World>, List<Triple<Vec3d, RegistryKey<World>, Vec3d>>> portalsMap = new HashMap<>();
        portalsMap.put(EARTH_DIMENSIONS.front(), overworldPortals);
        portalsMap.put(EARTH_DIMENSIONS.left(), leftPortals);
        portalsMap.put(EARTH_DIMENSIONS.right(), rightPortals);
        portalsMap.put(EARTH_DIMENSIONS.top(), topPortals);
        portalsMap.put(EARTH_DIMENSIONS.bottom(), bottomPortals);
        portalsMap.put(EARTH_DIMENSIONS.back(), backPortals);

        portalsMap.forEach((world, portals) -> portals.forEach(triple -> {
            Portal portal = Portal.entityType.create(this.worlds.get(world));
            portal.setOriginPos(
                    triple.getLeft()
                            .multiply(EARTH_DIMENSIONS.faceRadius() + EPSILON)
                            .add(EARTH_DIMENSIONS.getOffset(world) * EARTH_DIMENSIONS.faceRadius(), 0, 0)
            );
            portal.setDestinationDimension(triple.getMiddle());
            portal.setDestination(
                    triple.getRight()
                            .multiply(EARTH_DIMENSIONS.faceRadius() - EPSILON)
                            .add(EARTH_DIMENSIONS.getOffset(triple.getMiddle()) * EARTH_DIMENSIONS.faceRadius(), 0, 0));
            portal.setOrientationAndSize(
                    new Vec3d(triple.getLeft().z, 0, triple.getLeft().x),
                    new Vec3d(0, triple.getLeft().x != 0 ? 1 : -1, 0),
                    EARTH_DIMENSIONS.faceRadius() * 2,
                    OVERWORLD_HEIGHT * 2
            );
            PortalAPI.addGlobalPortal(this.worlds.get(world), portal);
        }));

    }



}
