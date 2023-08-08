package io.github.teampropulsive.types;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class GasCanister extends Item {
    public Identifier gas;
    public int maxCapacity;
    public int currentlyFilled;

    public GasCanister(Settings settings, Identifier gas, int maxCapacity) {
        super(settings);
        this.gas = gas;
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
