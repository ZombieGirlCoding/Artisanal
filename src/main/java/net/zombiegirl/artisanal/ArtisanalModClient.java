package net.zombiegirl.artisanal;

import net.fabricmc.api.ClientModInitializer;
import net.zombiegirl.artisanal.client.GreatSwordClientHandler;
import net.zombiegirl.artisanal.util.ModModelPredicates;

public class ArtisanalModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModelPredicates();
        GreatSwordClientHandler.initClient();
    }
}




