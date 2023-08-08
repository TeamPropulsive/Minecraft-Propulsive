package io.github.teampropulsive.handler;

import io.github.teampropulsive.types.Planet;
import net.minecraft.util.math.Vec3d;

public class PlanetGravityHandler {
    public static Vec3d currentPosition(Planet planet, Planet parent, long time) {
        double orbitRadius = planet.startingPos.distanceTo(parent.startingPos);
        double currentAngle = (2 * Math.PI / planet.orbitalPeriod) * time;
        return  new Vec3d( // This assumes it's orbiting a 2d plane around the planet - might want to change later
                parent.currentPos.x + (orbitRadius * Math.cos(currentAngle)),
                parent.currentPos.y,
                parent.currentPos.z + (orbitRadius * Math.sin(currentAngle))
        );
    }
}
