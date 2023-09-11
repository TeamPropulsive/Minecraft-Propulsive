package io.github.teampropulsive.block;

import io.github.teampropulsive.Items;
import io.github.teampropulsive.Propulsive;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.CubeFace;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;

@Environment(value= EnvType.CLIENT)
public class BlueprintTableBlockEntityRenderer implements BlockEntityRenderer<BlueprintTableBlockEntity>  {
    public BlueprintTableBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}
    @Override
    public void render(BlueprintTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        if (entity.show_bounding_box) {
            entity.update_area(entity.getWorld(), entity.getPos());
            BlockPos o = entity.get_pad_offset(entity.getWorld(), entity.getPos());
            if (o != null) {
                Vec3d offset = new Vec3d(o.getX() * (entity.pad_size + 1), o.getY() * (entity.pad_size + 1), o.getZ() * (entity.pad_size + 1));
                Vec3d a = new Vec3d(-entity.pad_size, 1, -entity.pad_size).add(offset);
                Vec3d b = new Vec3d(1 + entity.pad_size, 1 + entity.tower_size, 1 + entity.pad_size).add(offset);
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
                WorldRenderer.drawBox(matrices, vertexConsumer, a.x, a.y, a.z, b.x, b.y, b.z, 0.9f, 0.9f, 0.9f, 0.50f, 0.5f, 0.5f, 0.5f);
            }
        }
        matrices.translate(0.5, -0.25, 0.5);
        matrices.scale(4,4,4);
        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                new ItemStack(Blocks.BLUEPRINT_TABLE, 1), ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), light
        );

        matrices.pop();
    }
}
