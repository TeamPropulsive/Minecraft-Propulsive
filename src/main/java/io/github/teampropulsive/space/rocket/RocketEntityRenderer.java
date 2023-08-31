package io.github.teampropulsive.space.rocket;

import io.github.teampropulsive.PropulsiveClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RocketEntityRenderer extends MobEntityRenderer<RocketEntity, RocketEntityModel> {

    public RocketEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new RocketEntityModel(context.getPart(PropulsiveClient.MODEL_CUBE_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(RocketEntity entity) {
        return new Identifier("entitytesting", "textures/entity/cube/cube.png");
    }
}
