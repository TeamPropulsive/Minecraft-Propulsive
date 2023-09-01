package io.github.teampropulsive.util;

import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.types.Gas;

public class Gases {
    public static final Gas OXYGEN = new Gas(Propulsive.id("gas/oxygen"), 1.429);
    public static final Gas METHANE = new Gas(Propulsive.id("gas/methane"), 0.657);
    public static final Gas HYDROGEN = new Gas(Propulsive.id("gas/hydrogen"), 0.08988);
}
