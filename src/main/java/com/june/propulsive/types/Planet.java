package com.june.propulsive.types;

import com.june.propulsive.Propulsive;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
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
    float[] planetRot = { 0.0f, 0.0f };
    double[] planetPos = { 0.0, 0.0, 0.0 };

    boolean is3D = true;

    public Planet(double scale, double posX, double posY, double posZ, float horizontalRotation, float verticalRotation, Identifier texture) {
        // Will add more args in the future (Link a dimension, textures, etc)
        this.planetSize = scale;
        this.planetPos[0] = posX;
        this.planetPos[1] = posY;
        this.planetPos[2] = posZ;
        this.texture = texture;
        this.planetRot[0] = horizontalRotation;
        this.planetRot[1] = verticalRotation;
    }

    // Renders planet in space
    public void render() {
        WorldRenderEvents.END.register(context -> {
            if (context.world().getRegistryKey() == SPACE) {
                MinecraftClient client = MinecraftClient.getInstance();
                assert client.player != null;
                double distance = Math.pow(client.player.getX() - planetPos[0], 2) + Math.pow(client.player.getY() - planetPos[1], 2) + Math.pow((client.player.getZ() - planetPos[2]), 2);
                if (distance > PLANET_3D_RENDER_DIST && this.is3D) {
                    this.is3D = false;
                } else if (distance < PLANET_3D_RENDER_DIST && !this.is3D) {
                    this.is3D = true;
                }

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


                Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();

                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);

                float planetSize = (float) this.planetSize;

                if (this.is3D) {
                    // Back
                    matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

                    matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(this.planetRot[0]));
                    matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.planetRot[1]));

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

                } else {
                    // Makes the 2d render of the planet face you
                    Vec3d directionVector = new Vec3d( this.planetPos[0] - client.player.getX(), this.planetPos[1] - client.player.getY(), this.planetPos[2] - client.player.getZ()).normalize();
                    float horizontalAngle = (float) Math.toDegrees(Math.atan2(directionVector.z, directionVector.x)) - 90.0F;
                    float verticalAngle = (float) Math.toDegrees(Math.asin(directionVector.y));

                    matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(horizontalAngle));
                    matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(verticalAngle));

                    // Add the quad
                    buffer.vertex(positionMatrix, -planetSize, planetSize, -planetSize).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                    buffer.vertex(positionMatrix, -planetSize, -planetSize, -planetSize).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                    buffer.vertex(positionMatrix, planetSize, -planetSize, -planetSize).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                    buffer.vertex(positionMatrix, planetSize, planetSize, -planetSize).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();
                    Vec3d skyboxPos = directionVector.multiply(Math.sqrt(PLANET_3D_RENDER_DIST)).subtract(camera.getPos());
                    matrixStack.translate(skyboxPos.x, skyboxPos.y, skyboxPos.z);
                }

                RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
                RenderSystem.enableDepthTest();
                RenderSystem.setShaderTexture(0, this.texture);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                RenderSystem.disableCull();

                tessellator.draw();

                RenderSystem.depthFunc(GL11.GL_LEQUAL);
                RenderSystem.enableCull();
            }
        });
    }

    // Planet tick
    // *WARNING* Code here can have a large impact on performance! You have been warned!
    public void tick() {


    }


}
