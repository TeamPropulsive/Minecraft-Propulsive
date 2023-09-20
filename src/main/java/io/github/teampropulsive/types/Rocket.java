package io.github.teampropulsive.types;

import java.util.ArrayList;

public class Rocket {
    double mass = 0;
    double drag = 1.0;
    int cargoSlots = 0;
    int playerSeats = 0;
    ArrayList<RocketPart> parts;

    public Rocket(ArrayList<RocketPart> parts) {
        for (RocketPart part : parts) {
            this.mass += part.mass;
            this.drag *= part.dragMultiplier;
            this.cargoSlots += part.extraCargoSlots;
            this.playerSeats += part.extraPlayerSeats;


        }
    }
}
