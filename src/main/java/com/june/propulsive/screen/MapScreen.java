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

// TODO
public class MapScreen extends Screen {

    protected MapScreen(Text title) {
        super(title);
    }

    public MapScreen() {
        super(Text.of("Map Screen"));
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        double zoom = 1.0; // TODO : Make configurable (Scroll wheel?)


        context.getMatrices();
        Matrix4f positionMatrix = context.getMatrices().peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        double[] w = worldToMapCoords(this.client.player.getX(), this.client.player.getZ(), zoom);
        buffer.vertex(positionMatrix, (float) (-5.0 + w[0]), (float) (-5.0 + w[1]), 0).texture(0f, 0f).next();
        buffer.vertex(positionMatrix, (float) (-5.0 + w[0]), (float) (5.0 + w[1]), 0).texture(0f, 1f).next();
        buffer.vertex(positionMatrix, (float) (5.0 + w[0]), (float) (5.0 + w[1]), 0).texture(1f, 1f).next();
        buffer.vertex(positionMatrix, (float) (5.0 + w[0]), (float) (-5.0 + w[1]), 0).texture(1f, 0f).next();
        RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
        RenderSystem.setShaderTexture(0, new Identifier("propulsive:textures/gui/player.png"));
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        tessellator.draw();


        for (Planet planet : Propulsive.TICKABLE_PLANETS) {

                double[] pos = worldToMapCoords(planet.currentPos.x, planet.currentPos.z, zoom);
                double x = pos[0];
                double z = pos[1];
                double planetScale = planet.planetSize / zoom;
                Matrix4f pm = context.getMatrices().peek().getPositionMatrix();
                Tessellator t = Tessellator.getInstance();
                BufferBuilder b = t.getBuffer();
                b.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
                b.vertex(pm, (float) (x - planetScale), (float) (z - planetScale), 0).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                b.vertex(pm, (float) (x - planetScale), (float) (z + planetScale), 0).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                b.vertex(pm, (float) (x + planetScale), (float) (z + planetScale), 0).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                b.vertex(pm, (float) (x + planetScale), (float) (z - planetScale), 0).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();
                RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
                RenderSystem.setShaderTexture(0, planet.texture2d);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                t.draw();
                System.out.println(pos[0]);
            }
    }

    public double[] worldToMapCoords(double x, double y, double zoom) {
        double cosYaw = Math.cos(Math.toRadians(this.client.player.getYaw()));
        double sinYaw = Math.sin(Math.toRadians(this.client.player.getYaw()));

        double adjustedX = x - this.client.player.getX();
        double adjustedY = y - this.client.player.getZ();

        double rotatedX = -(adjustedX * cosYaw - adjustedY * sinYaw);
        double rotatedY = -(adjustedX * sinYaw + adjustedY * cosYaw);

        double screenX = (rotatedX / zoom + this.client.currentScreen.width / 2);
        double screenY = (rotatedY / zoom + this.client.currentScreen.height / 2);

        return new double[] { screenX, screenY };
    }

}