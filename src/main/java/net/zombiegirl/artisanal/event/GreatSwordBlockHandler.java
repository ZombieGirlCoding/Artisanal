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



public class GreatSwordBlockHandler {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(new ServerLivingEntityEvents.AllowDamage() {
            public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
                PlayerEntity player = (PlayerEntity) entity;

                if (!player.isUsingItem()) return true;
                if (player.getWorld() != null) {
                    player.getWorld().playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            SoundCategory.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }

                Hand hand = player.getActiveHand();
                EquipmentSlot slot = hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

                active.damage(1, player, slot);

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
                    player.setStackInHand(hand, now);
                }

                return false;
            }
        });
    }
}