package io.github.teampropulsive.propulsive;

import net.fabricmc.api.ClientModInitializer;

import java.util.ArrayList;
import java.util.List;

public class PropulsiveClient implements ClientModInitializer {
    public static List<String[]> capes = new ArrayList<>();
    @Override
    public void onInitializeClient() {
        // Capes
        capes.add(new String[]{"9e784cc7-753d-4655-89be-c196eed1a274", "primavera"});
        capes.add(new String[]{"56db0044-9fc8-4ae3-83d6-1adee75aa799", "jamiscus"});
        capes.add(new String[]{"cd285b68-5039-412b-8ef1-35470223221d", "potato"});
        capes.add(new String[]{"b641da4d-908f-4848-8576-3bbe56ab2efa", "haskeller"});
    }
}
