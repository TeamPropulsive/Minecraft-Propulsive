package io.github.teampropulsive.celestial;

import io.github.teampropulsive.types.AtmoCompositionGas;
import io.github.teampropulsive.types.Planet;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.teampropulsive.Propulsive;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class Gas extends Planet {
    public Gas(double scale, Vec3d pos, double orbitTime, double rotationTime, Vec2f rotationAngle, Identifier texture2d, Identifier texture3d, AtmoCompositionGas[] composition) {
        super(scale, pos, orbitTime, rotationTime, rotationAngle, texture2d, texture3d, composition);
    }

    @Override
    public void render() {
        // Render rings
        WorldRenderEvents.START.register(context -> {
            if (context.world().getRegistryKey() == Propulsive.SPACE) {
                Camera camera = context.camera();

                Vec3d targetPosition = this.currentPos;
                Vec3d transformedPosition = targetPosition.subtract(camera.getPos());

                MatrixStack matrixStack = new MatrixStack();
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
                matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

                Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();

                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
                buffer.vertex(positionMatrix, (float) (this.planetSize * 4.0), (float) (this.planetSize), (float) (this.planetSize * -4.0)).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
                buffer.vertex(positionMatrix, (float) (this.planetSize * -4.0), (float) (this.planetSize), (float) (this.planetSize * -4.0)).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
                buffer.vertex(positionMatrix, (float) (this.planetSize * -4.0), (float) (this.planetSize), (float) (this.planetSize * 4.0)).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
                buffer.vertex(positionMatrix, (float) (this.planetSize * 4.0), (float) (this.planetSize), (float) (this.planetSize * 4.0)).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

                RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
                RenderSystem.setShaderTexture(0, Propulsive.id("gas_default.png"));
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                RenderSystem.disableCull();
                RenderSystem.depthFunc(GL11.GL_ALWAYS);

                tessellator.draw();

                RenderSystem.depthFunc(GL11.GL_LEQUAL);
                RenderSystem.enableCull();
            }
        });
        super.render();
    }

    @Override
    public void collisionDetected(ServerPlayerEntity player) {}
}
