package com.june.propulsive.screen;

import com.june.propulsive.Propulsive;
import com.june.propulsive.types.Planet;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class MapScreen extends Screen {
    double zoom = 0.5;
    public MapScreen() { super(Text.of("Map Screen")); }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) { // Scroll wheel for zoom
        this.zoom -= scrollDelta * 0.1;
        return true;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Matrix4f pm = context.getMatrices().peek().getPositionMatrix();
        Tessellator t = Tessellator.getInstance();
        BufferBuilder b = t.getBuffer();
        b.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE); // Renders the player indicator
        assert (this.client != null ? this.client.currentScreen : null) != null;
        b.vertex(pm, (float) ((this.client.currentScreen.width / 2) - (11 / zoom)), (float) ((this.client.currentScreen.height / 2) - (10 / zoom)), 0).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
        b.vertex(pm, (float) ((this.client.currentScreen.width / 2) - (11 / zoom)), (float) ((this.client.currentScreen.height / 2) + (10 / zoom)), 0).color(1f, 1f, 1f, 1f).texture(0f, 1f).next();
        b.vertex(pm, (float) ((this.client.currentScreen.width / 2) + (10 / zoom)), (float) ((this.client.currentScreen.height / 2) + (10 / zoom)), 0).color(1f, 1f, 1f, 1f).texture(1f, 1f).next();
        b.vertex(pm, (float) ((this.client.currentScreen.width / 2) + (10 / zoom)), (float) ((this.client.currentScreen.height / 2) - (10 / zoom)), 0).color(1f, 1f, 1f, 1f).texture(1f, 0f).next();
        RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
        RenderSystem.setShaderTexture(0, new Identifier("propulsive:/textures/gui/player.png"));
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        t.draw();
        for (Planet planet : Propulsive.TICKABLE_PLANETS) { // Renders each of the planets relative to the player
            double[] pos = worldToMapCoords(planet.currentPos.x, planet.currentPos.z, zoom);
            double xa = pos[0];
            double za = pos[1];
            double planetScale = planet.planetSize / zoom;
            Matrix4f pma = context.getMatrices().peek().getPositionMatrix();
            Tessellator ta = Tessellator.getInstance();
            BufferBuilder ba = ta.getBuffer();
            ba.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
            ba.vertex(pma, (float) (xa - planetScale), (float) (za - planetScale), 0).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
            ba.vertex(pma, (float) (xa - planetScale), (float) (za + planetScale), 0).color(1f, 1f, 1f, 1f).texture(0f, 1f).next();
            ba.vertex(pma, (float) (xa + planetScale), (float) (za + planetScale), 0).color(1f, 1f, 1f, 1f).texture(1f, 1f).next();
            ba.vertex(pma, (float) (xa + planetScale), (float) (za - planetScale), 0).color(1f, 1f, 1f, 1f).texture(1f, 0f).next();
            RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
            RenderSystem.setShaderTexture(0, planet.texture2d);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            ta.draw();
        }
    }

    public double[] worldToMapCoords(double x, double y, double zoom) { // Calculates the position of planets relative to the player
        assert this.client != null;
        assert this.client.player != null;
        assert this.client.currentScreen != null;
        double cosYaw = Math.cos(Math.toRadians(this.client.player.getYaw()));
        double sinYaw = Math.sin(Math.toRadians(this.client.player.getYaw()));
        double adjustedX = x - this.client.player.getX();
        double adjustedY = y - this.client.player.getZ();
        double rotatedX = adjustedX * cosYaw + adjustedY * sinYaw;
        double rotatedY = adjustedX * sinYaw - adjustedY * cosYaw;
        double screenX = rotatedX / zoom + (double) this.client.currentScreen.width / 2;
        double screenY = rotatedY / zoom + (double) this.client.currentScreen.height / 2;
        return new double[] { screenX, screenY };
    }

}