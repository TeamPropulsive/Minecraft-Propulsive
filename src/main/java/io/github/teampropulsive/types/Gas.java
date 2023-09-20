package io.github.teampropulsive.types;

import net.minecraft.util.Identifier;

public class Gas {
    public Identifier identifier;
    public double density; // g/L at stp
    public Gas(Identifier type, double density) {
        this.identifier = type;
        this.density = density;
    }
}
