package io.github.teampropulsive.types;

import net.minecraft.block.Block;

public class GasTank extends Block {
    public Gas gas;
    public int maxCapacity;
    public int currentlyFilled;
    public GasTank(Settings settings, int maxCapacity) {
        super(settings);
        this.gas = null;
        this.maxCapacity = maxCapacity;
        this.currentlyFilled = 0;
    }

    public void addGas(int value) {
        if (this.currentlyFilled + value > this.maxCapacity) {
            this.currentlyFilled = maxCapacity;
            return;
        }
        this.currentlyFilled += value;
    }
    public void removeGas(int value) {
        if (this.currentlyFilled - value < 0) {
            this.currentlyFilled = 0;
            return;
        }
        this.currentlyFilled -= value;
    }

}
