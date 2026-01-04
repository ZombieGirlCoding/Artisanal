package net.zombiegirl.artisanal.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.zombiegirl.artisanal.item.ModItems;

public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.GREAT_SWORD, new GreatSwordItemRenderer());
    }
}
