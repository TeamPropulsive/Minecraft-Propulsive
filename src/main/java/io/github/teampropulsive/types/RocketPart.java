package io.github.teampropulsive.types;

public class RocketPart {
    public double mass;
    public double dragMultiplier;
    public int extraCargoSlots;
    public int extraPlayerSeats;

    public RocketPart(double mass, double dragMultiplier, int extraCargoSlots, int extraPlayerSeats) {
        this.mass = mass;
        this.dragMultiplier = dragMultiplier;
        this.extraCargoSlots = extraCargoSlots;
        this.extraPlayerSeats = extraPlayerSeats;
    }
}
