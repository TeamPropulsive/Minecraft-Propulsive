package com.june.propulsive.celestial;

import com.june.propulsive.types.Planet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

import static com.june.propulsive.Propulsive.SPACE;

public class Star extends Planet {
    public Star(double scale, double posX, double posY, double posZ, float horizontalRotation, float verticalRotation, Identifier texture2d, Identifier texture3d) {
        super(scale, posX, posY, posZ, horizontalRotation, verticalRotation, texture2d, texture3d);
    }

    @Override
    public void tick(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        for (ServerPlayerEntity player : players) {
            if (player.getWorld().getRegistryKey() == SPACE) {
                double distance = player.getPos().subtract(this.planetPos).length();
                if (distance < (this.planetSize * 2.01)) {
                    player.kill();
                }
            }

        }
    }
}
