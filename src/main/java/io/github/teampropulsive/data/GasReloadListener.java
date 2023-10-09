package io.github.teampropulsive.data;

import com.google.gson.Gson;

import com.google.gson.JsonObject;
import io.github.teampropulsive.Items;
import io.github.teampropulsive.Propulsive;
import io.github.teampropulsive.types.Gas;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static io.github.teampropulsive.Propulsive.gasDictionary;
import static io.github.teampropulsive.Propulsive.id;

public class GasReloadListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return id("gas");
    }

    @Override
    public void reload(ResourceManager manager) {
        // Clear caches here

        for (Map.Entry<Identifier, Resource> entry : manager.findResources("gases", path -> path.getPath().endsWith(".json")).entrySet()) {
            Identifier id = entry.getKey();
            Resource resource = entry.getValue();
            try (InputStream stream = resource.getInputStream()) { // Pretty sure this works, just gotta register it somehow (?)
                String contents = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                JsonObject object = JsonHelper.deserialize(contents);
                JsonObject densityJsonKey = object.getAsJsonObject("density");
                double density = densityJsonKey.getAsDouble();
                JsonObject identifierJsonKey = object.getAsJsonObject("density");
                String identifier = identifierJsonKey.getAsString();
                Gas gas = new Gas(new Identifier(resource.getResourcePackName(), identifier), density);
                gasDictionary.put(identifier, gas);
            } catch (Exception e) {
                Propulsive.LOGGER.error("Error occurred while loading gas " + id.toString(), e);
            }
        }
    }
}

