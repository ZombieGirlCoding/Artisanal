package net.zombiegirl.artisanal;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.zombiegirl.artisanal.client.GUIHeldVaryingItemRenderer;
import net.zombiegirl.artisanal.event.GreatSwordBlockHandler;
import net.zombiegirl.artisanal.item.ModItemGroups;
import net.zombiegirl.artisanal.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Artisanal implements ModInitializer {
    public static final String MOD_ID = "artisanal";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {

        ModItemGroups.registerItemGroups();


        ModItems.registerModItems();
        GreatSwordBlockHandler.register();

    }
}
