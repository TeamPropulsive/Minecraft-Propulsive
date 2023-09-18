package io.github.teampropulsive.screen;

import io.github.teampropulsive.Propulsive;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BlueprintTableScreen extends HandledScreen<BlueprintTableScreenHandler> {
    private static final Identifier TEXTURE = Propulsive.id("textures/gui/blueprint_table.png");
    private OutlineCheckboxWidget outlineCheckbox;

    private final ScreenHandlerListener listener = new ScreenHandlerListener() {
        @Override
        public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) { }

        @Override
        public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
            if (property == 0)
                outlineCheckbox.setChecked(value == 1);
        }
    };

    public BlueprintTableScreen(BlueprintTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.outlineCheckbox = new OutlineCheckboxWidget(this.x + 8, this.y + 138, 20, 20, Text.translatable("gui.propulsive.show_area"), this.handler.getOutlineEnabled());
        this.addDrawableChild(this.outlineCheckbox);
        this.handler.addListener(this.listener);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0x404040, false);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private class OutlineCheckboxWidget extends CheckboxWidget {
        public OutlineCheckboxWidget(int x, int y, int width, int height, Text message, boolean checked) {
            super(x, y, width, height, message, checked);
        }

        @Override
        public void onPress() {
            super.onPress();
            client.interactionManager.clickButton(handler.syncId, 0);

            handler.setOutlineEnabled(this.isChecked());
        }

        public void setChecked(boolean checked) {
            if (checked != this.isChecked())
                this.onPress();
        }
    }
}
