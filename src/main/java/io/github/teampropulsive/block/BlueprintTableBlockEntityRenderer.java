package io.github.teampropulsive.block;

import io.github.teampropulsive.util.BlockBox;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

@Environment(value= EnvType.CLIENT)
public class BlueprintTableBlockEntityRenderer implements BlockEntityRenderer<BlueprintTableBlockEntity>  {
    public BlueprintTableBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(BlueprintTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        if (entity.showBoundingBox) {
            entity.getConstructionArea().ifPresent(area -> {
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
                BlockPos pos = entity.getPos();
                BlockBox relativeArea = area.offset(pos.multiply(-1));
                WorldRenderer.drawBox(matrices, vertexConsumer,
                        relativeArea.minX, relativeArea.minY, relativeArea.minZ, relativeArea.maxX+1, relativeArea.maxY+1, relativeArea.maxZ+1,
                        0.9f, 0.9f, 0.9f, 0.50f, 0.5f, 0.5f, 0.5f);
            });
        }

        matrices.pop();
    }
}
