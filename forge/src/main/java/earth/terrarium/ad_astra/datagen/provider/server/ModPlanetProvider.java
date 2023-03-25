package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.system.GravitySystem;
import earth.terrarium.ad_astra.datagen.provider.base.ModCodecProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;


public class ModPlanetProvider extends ModCodecProvider<Planet> {
    public ModPlanetProvider(PackOutput packOutput) {
        super(packOutput, Planet.CODEC, PlanetData.PLANET_REGISTRY);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, Planet> consumer) {
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "overworld"), new Planet(Level.OVERWORLD, true, GravitySystem.DEFAULT_GRAVITY, 15));
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "moon"), new Planet(Planet.MOON, false, 1.625f, -160));
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "mars"), new Planet(Planet.MARS, false, 3.72076f, -65));
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "venus"), new Planet(Planet.VENUS, false, 8.87f, 464));
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "mercury"), new Planet(Planet.MERCURY, false, 3.7f, 167));
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "glacio"), new Planet(Planet.GLACIO, true, 3.721f, -46));
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "orbit"), new Planet(Planet.ORBIT, false, 0.01f, -270));
    }

    @Override
    public String getName() {
        return "Planets";
    }
}
