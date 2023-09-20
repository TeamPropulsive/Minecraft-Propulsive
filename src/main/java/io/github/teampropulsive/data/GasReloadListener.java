package io.github.teampropulsive.data;

import com.google.gson.Gson;

import io.github.teampropulsive.Items;
import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.types.Gas;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GasReloadListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return Propulsive.id("gas");
    }

    @Override
    public void reload(ResourceManager manager) {
        // Clear caches here

        for (Map.Entry<Identifier, Resource> entry : manager.findResources("gases", path -> path.getPath().endsWith(".json")).entrySet()) {
            Identifier id = entry.getKey();
            Resource resource = entry.getValue();
            try (InputStream stream = resource.getInputStream()) { // Pretty sure this works, just gotta register it somehow (?)
                Gas new_gas = new Gson().fromJson(new String(stream.readAllBytes(), StandardCharsets.UTF_8), Gas.class);

            } catch (Exception e) {
                Propulsive.LOGGER.error("Error occurred while loading gas " + id.toString(), e);
            }
        }
    }
}

