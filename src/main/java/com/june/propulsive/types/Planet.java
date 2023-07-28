package com.june.propulsive.types;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import static com.june.propulsive.Propulsive.*;
public class Planet {
    double planetSize;
    Identifier texture;
    double[] planetPos = { 0.0, 0.0, 0.0 };

    public Planet(double scale, double posX, double posY, double posZ, Identifier texture) {
        // Will add more args in the future (Link a dimension, textures, etc)
        planetSize = scale;
        planetPos[0] = posX;
        planetPos[1] = posY;
        planetPos[2] = posZ;
    }

    // Renders planet in space
    public void render() {
        WorldRenderEvents.END.register(context -> {
            if (context.world().getRegistryKey() == SPACE) {
                RenderSystem.depthMask(true);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.enableCull();
                Camera camera = context.camera();
                RenderSystem.enableDepthTest();
                Vec3d targetPosition = new Vec3d(this.planetPos[0], this.planetPos[1], this.planetPos[2]);
                Vec3d transformedPosition = targetPosition.subtract(camera.getPos());

                MatrixStack matrixStack = new MatrixStack();
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
                matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

                Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();

                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);

                float planetSize = (float) this.planetSize;
                // Back
                buffer.vertex(positionMatrix, -planetSize, planetSize, -planetSize).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                buffer.vertex(positionMatrix, -planetSize, -planetSize, -planetSize).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                buffer.vertex(positionMatrix, planetSize, -planetSize, -planetSize).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                buffer.vertex(positionMatrix, planetSize, planetSize, -planetSize).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

                //Front
                buffer.vertex(positionMatrix, -planetSize, planetSize, planetSize).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                buffer.vertex(positionMatrix, -planetSize, -planetSize, planetSize).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                buffer.vertex(positionMatrix, planetSize, -planetSize, planetSize).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                buffer.vertex(positionMatrix, planetSize, planetSize, planetSize).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

                // Top
                buffer.vertex(positionMatrix, planetSize, planetSize, -planetSize).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                buffer.vertex(positionMatrix, planetSize, planetSize, planetSize).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                buffer.vertex(positionMatrix, -planetSize, planetSize, planetSize).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                buffer.vertex(positionMatrix, -planetSize, planetSize, -planetSize).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

                // Bottom
                buffer.vertex(positionMatrix, -planetSize, -planetSize, planetSize).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                buffer.vertex(positionMatrix, -planetSize, -planetSize, -planetSize).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                buffer.vertex(positionMatrix, planetSize, -planetSize, -planetSize).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                buffer.vertex(positionMatrix, planetSize, -planetSize, planetSize).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

                // Left
                buffer.vertex(positionMatrix, planetSize, -planetSize, planetSize).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                buffer.vertex(positionMatrix, planetSize, -planetSize, -planetSize).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                buffer.vertex(positionMatrix, planetSize, planetSize, -planetSize).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                buffer.vertex(positionMatrix, planetSize, planetSize, planetSize).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

                // Right
                buffer.vertex(positionMatrix, -planetSize, planetSize, planetSize).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                buffer.vertex(positionMatrix, -planetSize, planetSize, -planetSize).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                buffer.vertex(positionMatrix, -planetSize, -planetSize, -planetSize).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                buffer.vertex(positionMatrix, -planetSize, -planetSize, planetSize).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

                RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
                RenderSystem.enableDepthTest();
                // Invalid namespace because the missing texture actually looks kind of cool
                RenderSystem.setShaderTexture(0, this.texture);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                RenderSystem.disableCull();

                tessellator.draw();

                RenderSystem.depthFunc(GL11.GL_LEQUAL);
                RenderSystem.enableCull();
            }
        });
    }
}
