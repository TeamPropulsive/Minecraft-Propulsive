package io.github.teampropulsive.physics

import io.github.teampropulsive.Propulsive.TICKABLE_PLANETS
import io.github.teampropulsive.types.Planet
import net.minecraft.util.math.Vec3d
import kotlin.math.pow

class Gravity {
    fun calculate(position : Vec3d): Vec3d? {
        val planet = get_parent_planet(position)
        if (planet != null) {
            var force = Vec3d(
                planet.currentPos.x - position.x,
                planet.currentPos.y - position.y,
                planet.currentPos.z - position.z
            )
            val d = planet.currentPos.squaredDistanceTo(position)
            val m = ((planet.planetSize + 1.0) * (planet.planetSize + 1.0) * (planet.planetSize + 1.0) * (planet.planetSize + 1.0))
            val g = 6.1
            force = force.normalize()
            force = force.multiply(m * g)
            return Vec3d(
                    force.x / d,
                    force.y / d,
                    force.z / d
            )
        }
        return Vec3d.ZERO
    }

    private fun get_parent_planet(position: Vec3d) : Planet? {
        var distance = Double.MAX_VALUE;
        var planet : Planet? = null
        for (p in TICKABLE_PLANETS) {
            val d = p.currentPos.distanceTo(position)
            val in_soi = d < ((p.planetSize + 1.0) * (p.planetSize + 1.0) * (p.planetSize + 1.0) * (p.planetSize + 1.0))
            if (in_soi && d < distance) {
                distance = d
                planet = p
            }
        }
        return planet;

    }
}