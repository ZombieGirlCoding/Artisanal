package net.zombiegirl.artisanal;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.zombiegirl.artisanal.datagen.ModModelProvider;
import net.zombiegirl.artisanal.datagen.ModRecipeProvider;

public class ArtisanalDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModRecipeProvider::new);

	}
}
