package io.github.teampropulsive.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.teampropulsive.Propulsive;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RocketReloadListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return Propulsive.id("rocket");
    }

    private static Map<String, BlockPredicate> parseKey(JsonObject key) {
        HashMap<String, BlockPredicate> parsedKey = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : key.entrySet()) {
            BlockPredicate predicate = BlockPredicate.fromJson(entry.getValue());
            parsedKey.put(entry.getKey(), predicate);
        }
        return parsedKey;
    }

    @Override
    public void reload(ResourceManager manager) {
        // Clear caches here

        for (Map.Entry<Identifier, Resource> entry : manager.findResources("rockets", path -> path.getPath().endsWith(".json")).entrySet()) {
            Identifier id = entry.getKey();
            Resource resource = entry.getValue();
            try (InputStream stream = resource.getInputStream()) {
                String contents = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                JsonObject object = JsonHelper.deserialize(contents);
                JsonObject jsonKey = object.getAsJsonObject("key");
                Map<String, BlockPredicate> key = parseKey(jsonKey);
                Propulsive.LOGGER.info(key.toString());

                Propulsive.LOGGER.info("Pretend like this is doing something useful with " + object.toString());
            } catch (Exception e) {
                Propulsive.LOGGER.error("Error occurred while loading rocket recipe " + id.toString(), e);
            }
        }
    }
}
