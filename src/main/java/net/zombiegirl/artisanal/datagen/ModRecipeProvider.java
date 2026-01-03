package net.zombiegirl.artisanal.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.zombiegirl.artisanal.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GREAT_SWORD)
                .pattern(" DD")
                .pattern("DND")
                .pattern("SD ")
                .input('D', Items.DIAMOND)
                .input('N', Blocks.NETHERITE_BLOCK)
                .input('S', Items.BREEZE_ROD)
                .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                .criterion("has_breeze_rod", conditionsFromItem(Items.BREEZE_ROD))
                .criterion("has_netherite_block", conditionsFromItem(Items.NETHERITE_BLOCK))
                .offerTo(exporter);

    }
}
