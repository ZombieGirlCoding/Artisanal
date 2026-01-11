package net.zombiegirl.artisanal.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.zombiegirl.artisanal.Artisanal;
import net.zombiegirl.artisanal.item.ModItems;
import net.zombiegirl.artisanal.util.ModModelPredicates;

public class GreatSwordClientHandler {

    // Call from your client entrypoint
    public static void initClient() {
        // Register model predicates (client-only)
        ModModelPredicates.registerModelPredicates();
        Artisanal.LOGGER.info("GreatSwordClientHandler: registered model predicates");

        // Client tick logger: helps confirm whether the client enters the 'using' state
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null) return;
            ClientPlayerEntity player = client.player;
            if (player == null) return;

            // Check if player is holding the great sword in either hand
            ItemStack main = player.getMainHandStack();
            ItemStack off = player.getOffHandStack();
            boolean holdingGreat = main.isOf(ModItems.GREAT_SWORD) || off.isOf(ModItems.GREAT_SWORD);

            if (!holdingGreat) return;

            boolean using = player.isUsingItem();
            ItemStack active = player.getActiveItem();

            // Log a helpful line (will print repeatedly while holding the item)
            Artisanal.LOGGER.info("GreatSword DEBUG: holding great_sword (main={}, off={}), using={}, activeIsGreat={}",
                    main.getItem(), off.getItem(), using, active.isOf(ModItems.GREAT_SWORD));
        });
    }
}
