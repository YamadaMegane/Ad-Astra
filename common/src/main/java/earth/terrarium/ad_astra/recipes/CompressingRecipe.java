package earth.terrarium.ad_astra.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import earth.terrarium.ad_astra.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class CompressingRecipe extends CookingRecipe {

    public CompressingRecipe(ResourceLocation id, Ingredient input, ItemStack output, int cookTime) {
        super(id, IngredientHolder.of(input), output, cookTime);
    }

    public static Codec<CompressingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.CODEC.fieldOf("input").forGetter(CompressingRecipe::getInputIngredient),
                ItemStackCodec.CODEC.fieldOf("output").forGetter(CompressingRecipe::getResultItem),
                Codec.INT.fieldOf("time").orElse(200).forGetter(CompressingRecipe::getCookTime)
        ).apply(instance, CompressingRecipe::new));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.COMPRESSING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.COMPRESSING_RECIPE.get();
    }
}