package net.zombiegirl.artisanal.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.zombiegirl.artisanal.item.custom.GreatSwordItem;

import javax.swing.*;


public class GreatSwordBlockHandler {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(new ServerLivingEntityEvents.AllowDamage() {
            public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
                if (!(entity instanceof PlayerEntity)) return true; // allow damage
                PlayerEntity player = (PlayerEntity) entity;

                // Must be actively using the item (right-click hold)
                if (!player.isUsingItem()) return true;
                if (player.getWorld() != null) {
                    player.getWorld().playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ITEM_SHIELD_BLOCK, // item.shield.block
                            SoundCategory.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }

// If attacker is a living entity holding an axe, disable blocking like a shield hit by an axe
                Entity attacker = source.getAttacker();
                if (attacker instanceof LivingEntity) {
                    ItemStack attackerHeld = ((LivingEntity) attacker).getMainHandStack();
                    if (attackerHeld.getItem() instanceof AxeItem) {
                        player.getItemCooldownManager().set(player.getActiveItem().getItem(), 125); // ticks cooldown
                        player.clearActiveItem();
                    }
                }

                ItemStack active = player.getActiveItem();
                if (active.isEmpty()) return true;
                if (!(active.getItem() instanceof GreatSwordItem)) return true;

                // DENY the damage: apply flat 1 durability loss to the held sword
                Hand hand = player.getActiveHand();
                EquipmentSlot slot = hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

                active.damage(1, player, slot);

                // If the item broke, clear the hand and play break sound; otherwise sync updated stack
                ItemStack now = player.getStackInHand(hand);
                if (now.isEmpty()) {
                    player.setStackInHand(hand, ItemStack.EMPTY);
                    if (player.getWorld() != null) {
                        player.getWorld().playSound(
                                null,
                                player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ENTITY_ITEM_BREAK,
                                SoundCategory.PLAYERS,
                                1.0F,
                                1.0F
                        );
                    }
                } else {
                    // force sync so client shows updated durability/cracks
                    player.setStackInHand(hand, now);
                }

                // return false to deny the damage (effectively negate it)
                return false;
            }
        });
    }
}