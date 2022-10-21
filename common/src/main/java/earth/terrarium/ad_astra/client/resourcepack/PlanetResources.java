package earth.terrarium.ad_astra.client.resourcepack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.dimension.ClientModSkies;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PlanetResources implements SynchronousResourceReloader {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public void reload(ResourceManager manager) {
        List<PlanetSkyRenderer> skyRenderers = new ArrayList<>();
        List<SolarSystem> solarSystems = new ArrayList<>();
        List<PlanetRing> planetRings = new ArrayList<>();
        List<Galaxy> galaxies = new ArrayList<>();

        // Sky Renderers
        for (Identifier id : manager.findResources("planet_resources/sky_renderers", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getAllResources(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        skyRenderers.add(PlanetSkyRenderer.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, AdAstra.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to load Ad Astra sky rendering assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        // Solar Systems
        for (Identifier id : manager.findResources("planet_resources/solar_systems", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getAllResources(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        solarSystems.add(SolarSystem.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, AdAstra.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to load Ad Astra solar system assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        for (Identifier id : manager.findResources("planet_resources/planet_rings", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getAllResources(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        planetRings.add(PlanetRing.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, AdAstra.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to load Ad Astra planet ring assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        for (Identifier id : manager.findResources("planet_resources/galaxy", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getAllResources(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        galaxies.add(Galaxy.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, AdAstra.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to load Ad Astra galaxy assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        solarSystems.sort(Comparator.comparing(SolarSystem::solarSystem));
        galaxies.sort(Comparator.comparing(Galaxy::galaxy));
        AdAstraClient.skyRenderers = skyRenderers;
        AdAstraClient.solarSystems = solarSystems;
        AdAstraClient.planetRings = planetRings;
        AdAstraClient.galaxies = galaxies;
        ClientModSkies.register();
    }
}
