package net.zombiegirl.artisanal.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.Artisanal;
import net.zombiegirl.artisanal.item.ModItems;
import net.zombiegirl.artisanal.item.custom.GreatSwordItem;
import org.jetbrains.annotations.Nullable;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public static final Model GREAT_SWORD_GUI = model("item/great_sword_gui", TextureKey.LAYER0);


    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.GREAT_SWORD, Models.HANDHELD);

    }

    private static Model model(String parent, TextureKey... keys) {
        return model(parent, null, keys);
    }

    private static Model model(String parent, TextureKey o, TextureKey[] keys) {
        return null;
    }

    private void registerTemplateWeapon(Model templateModel, @Nullable String name, Item item, ItemModelGenerator generator) {
        this.registerTemplateWeaponHandheld(templateModel, name, item, generator);
        this.registerTemplateWeaponInventory(templateModel, name, item, generator);
    }

    private void registerTemplateWeaponHandheld(Model templateModel, @Nullable String name, Item item, ItemModelGenerator generator) {
    }

    private void registerTemplateWeapon(Model templateModel, @Nullable String name, Identifier itemId, ItemModelGenerator generator) {
        this.registerTemplateWeaponHandheld(templateModel, name, itemId, generator);
        this.registerTemplateWeaponInventory(templateModel, name, itemId, generator);
    }

    private void registerTemplateWeaponHandheld(Model templateModel, @Nullable String name, Identifier itemId, ItemModelGenerator generator) {
    }

    private void registerTemplateWeaponInventory(Model templateModel, @Nullable String name, Item item, ItemModelGenerator generator) {
        registerTemplateWeaponInventory(templateModel, name, Registries.ITEM.getId(item), generator);
    }

    private void registerTemplateWeaponInventory(Model templateModel, @Nullable String name, Identifier itemId, ItemModelGenerator generator) {
        Identifier inventoryTexture = (name == null ? getItemSubId(itemId, "_gui") : getItemSubId(itemId, "_" + name + "_gui"));
        registerTemplateWeaponInventory(templateModel, name, itemId, inventoryTexture, generator);
    }

    public static Identifier getItemSubId(Identifier itemId, String suffix) {
        return itemId.withPath(path -> "item/" + path + suffix);
    }

    private void registerTemplateWeaponInventory(Model templateModel, @Nullable String name, Identifier itemModelId, Identifier inventoryTexture, ItemModelGenerator generator) {
        Identifier inventoryModelName = (name == null ? getItemSubId(itemModelId, "_gui") : getItemSubId(itemModelId, "_" + name + "_gui"));

        Models.HANDHELD.upload(inventoryModelName, TextureMap.layer0(inventoryTexture), generator.writer); // this is actually the inventory model
    }
}