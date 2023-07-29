package com.june.propulsive.entity;

import com.june.propulsive.Propulsive;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class PlanetEntityRenderer extends EntityRenderer {

    public PlanetEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return null;
    }


}