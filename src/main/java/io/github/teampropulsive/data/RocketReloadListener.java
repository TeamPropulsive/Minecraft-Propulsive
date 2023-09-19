package io.github.teampropulsive.data;

import io.github.teampropulsive.Propulsive;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.util.Map;

public class RocketReloadListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return Propulsive.id("rocket");
    }

    @Override
    public void reload(ResourceManager manager) {
        // Clear caches here

        for (Map.Entry<Identifier, Resource> entry : manager.findResources("rockets", path -> path.getPath().endsWith(".json")).entrySet()) {
            Identifier id = entry.getKey();
            Resource resource = entry.getValue();
            try (InputStream stream = resource.getInputStream()) {
                Propulsive.LOGGER.info("Pretend like this is doing something useful with " + id.toString());
            } catch (Exception e) {
                Propulsive.LOGGER.error("Error occurred while loading rocket recipe " + id.toString(), e);
            }
        }
    }
}
