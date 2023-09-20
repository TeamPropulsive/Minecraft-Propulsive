package io.github.teampropulsive.data;

import com.google.gson.Gson;
import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.types.Gas;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PlanetReloadListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return Propulsive.id("gas");
    }

    @Override
    public void reload(ResourceManager manager) {
        // Clear caches here

        for (Map.Entry<Identifier, Resource> entry : manager.findResources("planets", path -> path.getPath().endsWith(".json")).entrySet()) {
            Identifier id = entry.getKey();
            Resource resource = entry.getValue();
            try (InputStream stream = resource.getInputStream()) {

            } catch (Exception e) {
                Propulsive.LOGGER.error("Error occurred while loading planet " + id.toString(), e);
            }
        }
    }
}
