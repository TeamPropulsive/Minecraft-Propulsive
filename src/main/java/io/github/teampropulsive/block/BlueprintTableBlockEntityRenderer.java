package io.github.teampropulsive.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Environment(value= EnvType.CLIENT)
public class BlueprintTableBlockEntityRenderer implements BlockEntityRenderer<BlueprintTableBlockEntity>  {
    public BlueprintTableBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}
    @Override
    public void render(BlueprintTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        entity.update_area(entity.getWorld(), entity.getPos());
        BlockPos o = entity.get_pad_offset(entity.getWorld(), entity.getPos());
        if (o != null) {
            Vec3d offset = new Vec3d(
                    o.getX(),
                    o.getY(),
                    o.getZ()
            );
            offset.multiply(entity.pad_size);


            Vec3d a = new Vec3d(
                    0 + (-entity.pad_size ),
                    1,
                    1 + (entity.pad_size)
            ).add(offset);
            Vec3d b = new Vec3d(
                    1 + (entity.pad_size),
                    1 + entity.tower_size,
                    1 + (-entity.pad_size)
            ).add(offset);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
            WorldRenderer.drawBox(matrices, vertexConsumer, a.x, a.y, a.z, b.x, b.y, b.z, 0.9f, 0.9f, 0.9f, 0.50f, 0.5f, 0.5f, 0.5f);

        }



        matrices.pop();
    }
}
