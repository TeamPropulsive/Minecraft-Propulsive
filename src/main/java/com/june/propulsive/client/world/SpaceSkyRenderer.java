package com.june.propulsive.client.world;

import com.june.propulsive.types.Planet;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import static com.june.propulsive.Propulsive.TickablePlanets;

public class SpaceSkyRenderer implements DimensionRenderingRegistry.SkyRenderer {

    @Override
    public void render(WorldRenderContext context) {
        /*


        MinecraftClient client = MinecraftClient.getInstance();

        MatrixStack matrixStack = new MatrixStack();
        Vec3d cameraPos = client.player.getCameraPosVec(1.0F);

        for (Planet planet : TickablePlanets) {
            Vec3d targetPosition = planet.planetPos;
            Vec3d transformedPosition = targetPosition.subtract(cameraPos);

            matrixStack.push();
            matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

            // Project the world position to the screen coordinates

            int screenWidth = client.getWindow().getFramebufferWidth();
            int screenHeight = client.getWindow().getFramebufferHeight();
            Camera.Projection projection = client.gameRenderer.getBasicProjectionMatrix(client.player.getFovMultiplier()).project(transformedPosition, screenWidth, screenHeight);
            Camera.Projection projected = projection;

            // Draw the quad at the screen position
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();

            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
            buffer.vertex(projected.x, projected.y + 1, 0).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
            buffer.vertex(projected.x, projected.y, 0).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
            buffer.vertex(projected.x + 1, projected.y, 0).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
            buffer.vertex(projected.x + 1, projected.y + 1, 0).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

            tessellator.draw();

            matrixStack.pop();
        }
     */
    }
}
