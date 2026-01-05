package net.zombiegirl.artisanal;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.event.GreatSwordBlockHandler;
import net.zombiegirl.artisanal.item.ModItemGroups;
import net.zombiegirl.artisanal.item.ModItems;
import net.zombiegirl.artisanal.util.ModModelPredicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Artisanal implements ModInitializer {
    public static final String MOD_ID = "artisanal";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier MOD_ID(String s) {
        return null;
    }


    @Override
    public void onInitialize() {

        ModItemGroups.registerItemGroups();


        ModItems.registerModItems();
        GreatSwordBlockHandler.register();
        ModModelPredicates.registerModelPredicates();

    }
}
