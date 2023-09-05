package io.github.teampropulsive;

import io.github.teampropulsive.block.BlueprintTableBlockEntityRenderer;
import io.github.teampropulsive.client.world.SpaceDimensionEffects;
import io.github.teampropulsive.client.world.SpaceSkyRenderer;
import io.github.teampropulsive.keybind.ShipDownKeybind;
import io.github.teampropulsive.keybind.ShipThrottleDownKeybind;
import io.github.teampropulsive.keybind.ShipThrottleUpKeybind;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static io.github.teampropulsive.Propulsive.*;
import static io.github.teampropulsive.block.Blocks.BLUEPRINT_TABLE_BLOCK_ENTITY;
import static io.github.teampropulsive.keybind.DockShipKeybind.dockKeybindRegister;
import static io.github.teampropulsive.keybind.MapScreenKeybind.mapScreenKeybindRegister;

@Environment(EnvType.CLIENT)
public class PropulsiveClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropulsiveClient.class);
    public static List<String[]> capes = new ArrayList<>();
    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier("entitytesting", "cube"), "main");

    @Override
    public void onInitializeClient() {
        // Capes
        capes.add(new String[] { "9e784cc7-753d-4655-89be-c196eed1a274", "primavera" });
        capes.add(new String[] { "56db0044-9fc8-4ae3-83d6-1adee75aa799", "jamiscus" });
        capes.add(new String[] { "cd285b68-5039-412b-8ef1-35470223221d", "potato" });
        capes.add(new String[] { "b641da4d-908f-4848-8576-3bbe56ab2efa", "haskeller" });
        capes.addAll(getContributors("https://raw.githubusercontent.com/TeamPropulsive/Minecraft-Propulsive/main/contributors.txt", "/contributors.txt"));

        // Rendering
        DimensionRenderingRegistry.registerSkyRenderer(RegistryKey.of(RegistryKeys.WORLD, Propulsive.id("space")), new SpaceSkyRenderer());
        DimensionRenderingRegistry.registerDimensionEffects(Propulsive.id("space"), new SpaceDimensionEffects());
        DimensionRenderingRegistry.registerSkyRenderer(RegistryKey.of(RegistryKeys.WORLD, Propulsive.id("moon")), new SpaceSkyRenderer());
        DimensionRenderingRegistry.registerDimensionEffects(Propulsive.id("moon"), new SpaceDimensionEffects());

        BlockEntityRendererFactories.register(BLUEPRINT_TABLE_BLOCK_ENTITY, BlueprintTableBlockEntityRenderer::new);

        MOON.render();
        SUN.render();
        EARTH.render();

        // Key binds
        mapScreenKeybindRegister();
        dockKeybindRegister();
        ShipThrottleUpKeybind.shipThrottleUpKeybind();
        ShipThrottleDownKeybind.shipThrottleDownKeybind();
        ShipDownKeybind.shipDownKeybind();

    }

    // Returns contributors from git repo or from the fallback list in the JAR if network is unavailable
    public static List<String[]> getContributors(String fileUrl, String fallback) {
        try {
            List<String[]> contentWithContributor = new ArrayList<>();
            URL url = new URL(fileUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                contentWithContributor.add(new String[]{line, "contributor"});
            }
            reader.close();
            return contentWithContributor;
        } catch (IOException e) {
            LOGGER.warn("Could not load contributors list over the network ({}) - using fallback", e.toString());
            List<String[]> contentWithContributor = new ArrayList<>();

            try (InputStream fallbackStream = PropulsiveClient.class.getResourceAsStream(fallback)) {
                if (fallbackStream == null) {
                    LOGGER.warn("Mod JAR does not contain fallback contributors list - contributor capes will not be shown!");
                    return contentWithContributor;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(fallbackStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    contentWithContributor.add(new String[]{line, "contributor"});
                }
                reader.close();
            } catch (IOException ex) {
                LOGGER.warn("Failed to load fallback contributors list ({}) - contributor capes will not be shown!", ex.toString());
            }

            return contentWithContributor;
        }
    }
}
