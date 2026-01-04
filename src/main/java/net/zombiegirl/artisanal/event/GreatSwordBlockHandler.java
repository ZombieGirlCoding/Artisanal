package net.zombiegirl.artisanal.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.zombiegirl.artisanal.item.custom.GreatSwordItem;

import java.util.Set;


public class GreatSwordBlockHandler {



    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(new ServerLivingEntityEvents.AllowDamage() {
            public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
                if (!(entity instanceof PlayerEntity)) return true; // allow damage for non-players
                PlayerEntity player = (PlayerEntity) entity;

                // Only consider blocking when the player is actively using the item
                if (!player.isUsingItem()) return true;

                // Find the attacker / immediate source entity (projectiles often appear as source but attacker may be null)
                Entity attacker = source.getAttacker();
                if (attacker == null) {
                    attacker = source.getSource();
                }

                // If there's no attacker or source entity, this is likely environmental/fire/etc. — don't block
                if (attacker == null) return true;

                // Ensure the active item is a GreatSwordItem
                ItemStack active = player.getActiveItem();
                if (active.isEmpty()) return true;
                if (!(active.getItem() instanceof GreatSwordItem)) return true;

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

                // If attacker is an axe-wielder we disable blocking like shields do
                if (attacker instanceof LivingEntity) {
                    ItemStack attackerHeld = ((LivingEntity) attacker).getMainHandStack();
                    if (attackerHeld.getItem() instanceof AxeItem) {
                        if (!player.getActiveItem().isEmpty()) {
                            player.getItemCooldownManager().set(player.getActiveItem().getItem(), 125); // ticks cooldown
                        }
                        player.clearActiveItem();
                    }
                }

                // At this point: valid front-facing attack and player is using the greatsword -> block
                if (player.getWorld() != null) {
                    player.getWorld().playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ITEM_SHIELD_BLOCK,
                            SoundCategory.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }

                // Apply durability loss: use a safe consumer (don't call mapping-specific helpers here)
                Hand hand = player.getActiveHand();
                EquipmentSlot slot = hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

                // Use an empty consumer; we handle broken-item logic immediately after so it's safe across mappings.
                active.damage(1, player, slot);


                // Sync/clear the item in hand if broken; otherwise force update so client shows cracks/durability
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

                // Deny the damage (we've blocked it)
                return false;
            }
        });
    }
}