package net.zombiegirl.artisanal.client;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class GreatSwordItemRenderer implements BuiltinItemRenderer {
    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
        private static final ModelIdentifier GUI_MODEL_ID = new ModelIdentifier("artisanal:great_sword_gui", "inventory");
        private static final ModelIdentifier HANDHELD_MODEL_ID = new ModelIdentifier("artisanal:great_sword_handheld", "inventory");

        @Override
        public void render(ItemStack stack, Mode renderMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
            MinecraftClient client = MinecraftClient.getInstance();

            ModelIdentifier chosen = (renderMode == Mode.GUI || renderMode == Mode.GROUND || renderMode == Mode.FIXED)
                    ? GUI_MODEL_ID : HANDHELD_MODEL_ID;

            BakedModel model = client.getBakedModelManager().getModel(chosen);
            ItemRenderer itemRenderer = client.getItemRenderer();

            // Common 1.21-compatible render call:
            // renderItem(ItemStack stack, ModelTransformation.Mode transformType, boolean leftHanded,
            //            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model)
            itemRenderer.renderItem(stack, renderMode, false, matrices, vertexConsumers, light, overlay, model);

            // If the above method signature does not exist in your mappings, you can try one of the alternatives:
            // Alternative A:
            // itemRenderer.renderBakedItemModel(model, light, overlay, matrices, vertexConsumers);
            //
            // Alternative B:
            // itemRenderer.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumers);
        }
    }
}
