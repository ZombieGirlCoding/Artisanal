package net.zombiegirl.artisanal.util;


import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.Artisanal;
import net.zombiegirl.artisanal.item.ModItems;

public class ModModelPredicates {
    public static void registerModelPredicates() {

        registerCustomSword(ModItems.GREAT_SWORD);

    }

       private static void registerCustomSword (Item item){
            ModelPredicateProviderRegistry.register(
                    item,
                    Identifier.ofVanilla("blocking"),
                    (stack, world, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );
       }


}


