package net.zombiegirl.artisanal.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.zombiegirl.artisanal.item.ModItems;

public class ArtisanalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register a builtin item renderer for the Great Sword; the renderer is implemented below.
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.GREAT_SWORD, new GreatSwordItemRenderer());
    }
}
