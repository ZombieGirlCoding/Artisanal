package net.zombiegirl.artisanal.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.Artisanal;
import net.zombiegirl.artisanal.item.custom.GreatSwordItem;

public class ModItems {

    public static final Item GREAT_SWORD = registerItem("great_sword", new GreatSwordItem());


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Artisanal.MOD_ID, name), item);
    }


    public static void registerModItems () {
        Artisanal.LOGGER.info("Registering Mod Item For" + Artisanal.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(GREAT_SWORD);
        });
    }

}
