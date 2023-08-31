package io.github.teampropulsive.space.station;

import io.github.teampropulsive.PropulsiveClient;
import io.github.teampropulsive.space.rocket.RocketEntity;
import io.github.teampropulsive.space.rocket.RocketEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class StationEntityRenderer extends MobEntityRenderer<StationEntity, StationEntityModel> {

    public StationEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new StationEntityModel(context.getPart(PropulsiveClient.MODEL_CUBE_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(StationEntity entity) {
        return new Identifier("propulsive", "textures/spacecraft/"+entity.variation.getPath()+"/texture.png");
    }
}
