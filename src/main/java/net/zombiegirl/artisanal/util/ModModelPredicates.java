package net.zombiegirl.artisanal.util;


import com.google.common.collect.Maps;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.zombiegirl.artisanal.Artisanal;
import net.zombiegirl.artisanal.item.ModItems;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.CLongBuffer;

import java.util.Map;

public class ModModelPredicates extends ModelPredicateProviderRegistry {
    private static final Map<Identifier, ModelPredicateProvider> GLOBAL = Maps.<Identifier, ModelPredicateProvider>newHashMap();
    public static void registerModelPredicates() {

        registerCustomSword(ModItems.GREAT_SWORD);

    }

    public static ClampedModelPredicateProvider register(Identifier id, ClampedModelPredicateProvider provider) {
        GLOBAL.put(id, provider);
        return provider;
    }

    private double time;
    private double step;
    private long lastTick;


    public float unclampedCall(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i) {
        Entity entity = (Entity)(livingEntity != null ? livingEntity : itemStack.getHolder());
        if (entity == null) {
            return 0.0F;
        } else {
            if (clientWorld == null && entity.getWorld() instanceof ClientWorld) {
                clientWorld = (ClientWorld)entity.getWorld();
            }

            if (clientWorld == null) {
                return 0.0F;
            } else {
                double d;
                if (clientWorld.getDimension().natural()) {
                    d = clientWorld.getSkyAngle(1.0F);
                } else {
                    d = Math.random();
                }

                d = this.getTime(clientWorld, d);
                return (float)d;
            }
        }
    }
    private double getTime(World world, double skyAngle) {
        if (world.getTime() != this.lastTick) {
            this.lastTick = world.getTime();
            double d = skyAngle - this.time;
            d = MathHelper.floorMod(d + 0.5, 1.0) - 0.5;
            this.step += d * 0.1;
            this.step *= 0.9;
            this.time = MathHelper.floorMod(this.time + this.step, 1.0);
        }

        return this.time;
    }

    private static void registerCustomSword (Item item){
            ModelPredicateProviderRegistry.register(
                    ModItems.GREAT_SWORD,
                    Identifier.ofVanilla("blocking"),
                    (stack, world, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );
       }


}



