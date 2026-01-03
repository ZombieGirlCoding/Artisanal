package net.zombiegirl.artisanal.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.Artisanal;

public class ModItemGroups {
    public static final ItemGroup ARTISNAL_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Artisanal.MOD_ID, "artisinal_items"),
            FabricItemGroup.builder().icon(()-> new ItemStack(ModItems.GREAT_SWORD))
                    .displayName(Text.translatable("Itemgroup.Artisanal"))
                    .entries((displayContext, entries) ->{

                        entries.add(ModItems.GREAT_SWORD);


                    }).build());

    public static void registerItemGroups() {
        Artisanal.LOGGER.info("Registering Item Groups for" + Artisanal.MOD_ID);
    }

}
