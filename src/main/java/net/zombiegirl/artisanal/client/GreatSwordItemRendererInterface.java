package net.zombiegirl.artisanal.client;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public interface GreatSwordItemRendererInterface extends BuiltinItemRenderer {
    void render(ItemStack stack, ModelTransformation.Mode renderMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay);

    @Override
    void render(ItemStack itemStack, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1);
}
