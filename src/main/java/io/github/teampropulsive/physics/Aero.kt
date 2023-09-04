package io.github.teampropulsive.physics

import io.github.teampropulsive.types.AtmoCompositionGas
import net.minecraft.util.math.Vec3d

class Aero {
    fun calculate_aero(velocity : Vec3d, drag_coefficient : Double, area_in_direction : Double, atmo : ArrayList<AtmoCompositionGas>): Vec3d {
        var average_density = 0.0;
        for (gas in atmo) {
            average_density += gas.gas.density * gas.quantity
        }
        average_density /= atmo.size.toDouble()
        val force = velocity.multiply(velocity).multiply(average_density).multiply(0.5).multiply(drag_coefficient).multiply(area_in_direction)
        return force
    }
}