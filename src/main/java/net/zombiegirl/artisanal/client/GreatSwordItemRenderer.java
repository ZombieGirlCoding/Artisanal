package net.zombiegirl.artisanal.client;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelIdentifier;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.Artisanal;

/**
 * Variant A — uses json.ModelTransformation.Mode in the method signature.
 * Try this first (most common).
 */
public class GreatSwordItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private BakedModel guiModel = null;
    private BakedModel handModel = null;

    private final ModelResourceLocation guiLocation = new ModelResourceLocation(Identifier.of(Artisanal.MOD_ID, "item/great_sword_inventory"), "inventory");
    private final ModelResourceLocation handLocation = new ModelResourceLocation(Identifier.of(Artisanal.MOD_ID, "item/great_sword"), "inventory");

    private void ensureModelsLoaded() {
        if (guiModel != null && handModel != null) return;
        ModelManager mm = Minecraft.getInstance().getModelManager();
        try {
            if (guiModel == null) guiModel = mm.getModel(guiLocation);
        } catch (Exception e) {
            guiModel = mm.getMissingModel();
        }
        try {
            if (handModel == null) handModel = mm.getModel(handLocation);
        } catch (Exception e) {
            handModel = mm.getMissingModel();
        }
    }

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        ensureModelsLoaded();

        // Choose GUI model for inventory rendering, otherwise use hand model.
        BakedModel toRender = (transformType == ItemTransforms.TransformType.GUI) ? guiModel : handModel;

        // Use Minecraft's ItemRenderer to render the baked model with the usual lighting/overlay.
        Minecraft.getInstance().getItemRenderer().render(stack, transformType, false, matrices, vertexConsumers, light, overlay, toRender);
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

    }
}