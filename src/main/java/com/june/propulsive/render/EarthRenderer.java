package com.june.propulsive.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import static com.june.propulsive.Propulsive.OVERWORLD_SPACE_SIZE;

public class EarthRenderer {

    // TODO : Fix weird texturing
    public static void render() {
        WorldRenderEvents.END.register(context -> {
            RenderSystem.depthMask(true);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();

            Camera camera = context.camera();
            RenderSystem.enableDepthTest();
            Vec3d targetPosition = new Vec3d(0, 100, 0);
            Vec3d transformedPosition = targetPosition.subtract(camera.getPos());

            MatrixStack matrixStack = new MatrixStack();
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
            matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

            Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();

            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
            // Back
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

            //Front
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

            // Top
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

            // Bottom
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

            // Left
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
            buffer.vertex(positionMatrix, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

            // Right
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
            buffer.vertex(positionMatrix, -OVERWORLD_SPACE_SIZE, -OVERWORLD_SPACE_SIZE, OVERWORLD_SPACE_SIZE).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

            RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
            RenderSystem.enableDepthTest();
            // Invalid namespace because the missing texture actually looks kind of cool
            RenderSystem.setShaderTexture(0, new Identifier("p", "textures/blank.png"));
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableCull();

            tessellator.draw();

            RenderSystem.depthFunc(GL11.GL_LEQUAL);
            RenderSystem.enableCull();
        });
    }

}
