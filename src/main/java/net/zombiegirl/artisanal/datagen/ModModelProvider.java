package net.zombiegirl.artisanal.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.Artisanal;
import net.zombiegirl.artisanal.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public static final Model BIG_WEAPON_IN_HAND = model("item/template_big_weapon_in_hand", "_in_hand", TextureKey.LAYER0);

    private static Model model(String parent, @Nullable String variant, TextureKey... keys) {
        return new Model(Optional.of(Artisanal.id(parent)), Optional.ofNullable(variant), keys);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }

    private void registerTemplateWeaponInventory(Model templateModel, @Nullable String name, Item item, ItemModelGenerator generator) {
        registerTemplateWeaponInventory(templateModel, name, Registries.ITEM.getId(item), generator);
    }

    private void registerTemplateWeaponInventory(Model templateModel, @Nullable String name, Identifier itemId, ItemModelGenerator generator) {
        Identifier inventoryTexture = (name == null ? getItemSubId(itemId, "_inventory") : getItemSubId(itemId, "_" + name + "_inventory"));
        registerTemplateWeaponInventory(templateModel, name, itemId, inventoryTexture, generator);
    }

    private void registerTemplateWeaponInventory(Model templateModel, @Nullable String name, Identifier itemModelId, Identifier inventoryTexture, ItemModelGenerator generator) {
        Identifier inventoryModelName = (name == null ? getItemSubId(itemModelId, "_inventory") : getItemSubId(itemModelId, "_" + name + "_inventory"));

        Models.HANDHELD.upload(inventoryModelName, TextureMap.layer0(inventoryTexture), generator.writer);
    }

    private Identifier getItemSubId(Identifier itemId, String suffix) {
        return itemId.withPath(path -> "item/" + path + suffix);
    }
}