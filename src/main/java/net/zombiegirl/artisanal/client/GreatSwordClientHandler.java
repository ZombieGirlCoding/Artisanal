package net.zombiegirl.artisanal.client;

import com.google.common.base.MoreObjects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.zombiegirl.artisanal.item.ModItems;

import java.util.Objects;

import static net.minecraft.util.UseAction.BLOCK;

public class GreatSwordClientHandler {
    private ItemStack mainHand = ItemStack.EMPTY;
    private ItemStack offHand = ItemStack.EMPTY;
    private float equipProgressMainHand;
    private float prevEquipProgressOffHand;
    private float prevEquipProgressMainHand;
    private float equipProgressOffHand;
    private MinecraftClient client;
    private GreatSwordClientHandler firstPersonRenderer;

    public static void initClient() {
    }


    public void renderItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light) {
        float f = player.getHandSwingProgress(tickDelta);
        Hand hand = MoreObjects.firstNonNull(player.preferredHand, Hand.MAIN_HAND);
        float g = MathHelper.lerp(tickDelta, player.prevPitch, player.getPitch());
        float h = MathHelper.lerp(tickDelta, player.lastRenderPitch, player.renderPitch);
        float i = MathHelper.lerp(tickDelta, player.lastRenderYaw, player.renderYaw);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((player.getPitch(tickDelta) - h) * 0.1F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((player.getYaw(tickDelta) - i) * 0.1F));
        if (HandRenderType.renderMainHand) {
            float j = hand == Hand.MAIN_HAND ? f : 0.0F;
            float k = 1.0F - MathHelper.lerp(tickDelta, this.prevEquipProgressMainHand, this.equipProgressMainHand);
            this.renderFirstPersonItem(player, tickDelta, g, Hand.MAIN_HAND, j, this.mainHand, k, matrices, vertexConsumers, light);
        }

        if (HandRenderType.renderOffHand) {
            float j = hand == Hand.OFF_HAND ? f : 0.0F;
            float k = 1.0F - MathHelper.lerp(tickDelta, this.prevEquipProgressOffHand, this.equipProgressOffHand);
            this.renderFirstPersonItem(player, tickDelta, g, Hand.OFF_HAND, j, this.offHand, k, matrices, vertexConsumers, light);
        }

        vertexConsumers.draw();
    }
    private void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress) {
        int i = arm == Arm.RIGHT ? 1 : -1;
        matrices.translate(i * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
    }

    private void renderFirstPersonItem(
            AbstractClientPlayerEntity player,
            float tickDelta,
            float pitch,
            Hand hand,
            float swingProgress,
            ItemStack item,
            float equipProgress,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light
    ) {
        boolean bl = hand == Hand.MAIN_HAND;
        Arm arm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
        matrices.push();

        if (item.isOf(ModItems.GREAT_SWORD))  {
            boolean bl2 = arm == Arm.RIGHT;
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                int l = bl2 ? 1 : -1;
                if (Objects.requireNonNull(item.getUseAction()) == BLOCK) {
                    this.applyEquipOffset(matrices, arm, equipProgress);
                }
        }
        matrices.pop();
    }


    }

    public void updateHeldItems() {
        this.prevEquipProgressMainHand = this.equipProgressMainHand;
        this.prevEquipProgressOffHand = this.equipProgressOffHand;
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        ItemStack itemStack = clientPlayerEntity.getMainHandStack();
        ItemStack itemStack2 = clientPlayerEntity.getOffHandStack();
        if (ItemStack.areEqual(this.mainHand, itemStack)) {
            this.mainHand = itemStack;
        }        this.firstPersonRenderer.updateHeldItems();




        if (ItemStack.areEqual(this.offHand, itemStack2)) {
            this.offHand = itemStack2;
        }

        if (clientPlayerEntity.isRiding()) {
            this.equipProgressMainHand = MathHelper.clamp(this.equipProgressMainHand - 0.4F, 0.0F, 1.0F);
            this.equipProgressOffHand = MathHelper.clamp(this.equipProgressOffHand - 0.4F, 0.0F, 1.0F);
        } else {
            float f = clientPlayerEntity.getAttackCooldownProgress(1.0F);
            this.equipProgressMainHand = this.equipProgressMainHand
                    + MathHelper.clamp((this.mainHand == itemStack ? f * f * f : 0.0F) - this.equipProgressMainHand, -0.4F, 0.4F);
            this.equipProgressOffHand = this.equipProgressOffHand + MathHelper.clamp((this.offHand == itemStack2 ? 1 : 0) - this.equipProgressOffHand, -0.4F, 0.4F);
        }

        if (this.equipProgressMainHand < 0.1F) {
            this.mainHand = itemStack;
        }

        if (this.equipProgressOffHand < 0.1F) {
            this.offHand = itemStack2;
        }
    }

        @Environment(EnvType.CLIENT)
         enum HandRenderType {
            RENDER_MAIN_HAND_ONLY(),
            RENDER_OFF_HAND_ONLY();

             static boolean renderMainHand;
             static boolean renderOffHand;
             
             HandRenderType() {
            }
            public static HandRenderType shouldOnlyRender(Hand hand) {
                return hand == Hand.MAIN_HAND ? RENDER_MAIN_HAND_ONLY : RENDER_OFF_HAND_ONLY;
            }
    }
}