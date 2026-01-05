package net.zombiegirl.artisanal.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.item.ModItems;

public class ArtisanalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Identifier greatSwordId = Identifier.of("artisanal", "great_sword");
        GUIHeldVaryingItemRenderer renderer = new GUIHeldVaryingItemRenderer(greatSwordId);

        // Register built-in renderer for the item
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.GREAT_SWORD, renderer);

        // Register the renderer as a resource reload listener so it can refresh models on /reload
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(renderer);
    }
}
