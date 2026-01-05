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
import net.minecraft.util.math.Vec3d;
import net.zombiegirl.artisanal.item.custom.GreatSwordItem;

import javax.swing.*;


public class GreatSwordBlockHandler {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(new ServerLivingEntityEvents.AllowDamage() {
            public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
                if (!(entity instanceof PlayerEntity)) return true; // allow damage
                PlayerEntity player = (PlayerEntity) entity;
                Entity attacker = source.getAttacker();
                Hand hand = player.getActiveHand();
                EquipmentSlot slot = hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                ItemStack active = player.getActiveItem();


                // Must be actively using the item (right-click hold)


                if (!player.isBlocking()) return true;

                if (attacker == null) {
                    attacker = source.getSource();
                }

                if (attacker == null) return true;


                // Directional check: only block if attacker/projectile is in front of the player
                Vec3d lookVec = player.getRotationVec(1.0F).normalize();
                double attackerX = attacker.getX();
                double attackerY = attacker.getEyeY();
                double attackerZ = attacker.getZ();
                Vec3d toAttacker = new Vec3d(attackerX - player.getX(), attackerY - player.getEyeY(), attackerZ - player.getZ());
                if (toAttacker.lengthSquared() == 0.0D) return true; // degenerate case -> don't block
                toAttacker = toAttacker.normalize();

                double dot = lookVec.dotProduct(toAttacker);

                // Threshold: require attacker to be in front half of the player
                double threshold = 0.0D; // adjust to taste: 0.0 = front half, 0.5 = ~60° cone
                if (dot <= threshold) {
                    // Attacker is not in front enough — allow damage
                    return true;
                }



                // If attacker is a living entity holding an axe, disable blocking like a shield hit by an axe

                if (attacker instanceof LivingEntity) {
                    ItemStack attackerHeld = ((LivingEntity) attacker).getMainHandStack();
                    if (attackerHeld.getItem() instanceof AxeItem) {
                        player.getItemCooldownManager().set(player.getActiveItem().getItem(), 125); // ticks cooldown
                        player.clearActiveItem();
                        active.damage(1, player, slot);
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
                        active.damage(1, player, slot);
                        if (player.getWorld() != null) {
                            if (player.isBlocking()) {
                                player.getWorld().playSound(
                                        null,
                                        player.getX(), player.getY(), player.getZ(),
                                        SoundEvents.ITEM_SHIELD_BLOCK, // item.shield.block
                                        SoundCategory.PLAYERS,
                                        1.0F,
                                        1.0F
                                );
                            }
                        }
                    }

                    return false;
                }
                // Find the attacker / immediate source entity (projectiles often appear as source but attacker may be null)
                if (active.isEmpty()) return true;
                return !(active.getItem() instanceof GreatSwordItem);


                // return false to deny the damage (effectively negate it)
            }
        });
    }
}