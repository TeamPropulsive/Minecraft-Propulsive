package io.github.teampropulsive.screen;

import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.block.blueprint_table.BlueprintTableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;

public class BlueprintTableScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public BlueprintTableScreenHandler(int syncId, PacketByteBuf buf) {
        this(syncId, new ArrayPropertyDelegate(BlueprintTableBlockEntity.PROPERTY_COUNT));
        this.setOutlineEnabled(buf.readBoolean());
    }

    public BlueprintTableScreenHandler(int syncId, PropertyDelegate propertyDelegate) {
        super(Propulsive.BLUEPRINT_TABLE_SCREEN, syncId);

        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        switch (id) {
            case 0: {
                this.toggleOutline();
                return true;
            }
            default:
                return false;
        }
    }

    private void toggleOutline() {
        this.setOutlineEnabled(!this.getOutlineEnabled());
    }

    public boolean getOutlineEnabled() {
        return this.propertyDelegate.get(0) == 1;
    }

    public void setOutlineEnabled(boolean enabled) {
        this.propertyDelegate.set(0, enabled ? 1 : 0);
        this.sendContentUpdates();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
