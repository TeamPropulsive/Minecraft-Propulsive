package io.github.teampropulsive.physics

import io.github.teampropulsive.types.AtmoCompositionGas
import net.minecraft.util.math.Vec3d

class Aero {
    fun calculate_aero(
        velocity: Vec3d,
        dragCoefficient: Double,
        areaInDirection: Double,
        atmo: ArrayList<AtmoCompositionGas>
    ): Vec3d {
        var averageDensity = 0.0;
        for (gas in atmo)
            averageDensity += gas.gas.density * gas.quantity
        averageDensity /= atmo.size.toDouble()
        return velocity
            .multiply(velocity)
            .multiply(averageDensity)
            .multiply(0.5)
            .multiply(dragCoefficient)
            .multiply(areaInDirection)
    }
}