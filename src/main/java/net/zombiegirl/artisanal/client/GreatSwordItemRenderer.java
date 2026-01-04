package net.zombiegirl.artisanal.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Identifier;
import net.zombiegirl.artisanal.Artisanal;

/**
 * Dynamic renderer that picks one baked model when shown in GUI (inventory)
 * and another when rendered in hand.
 */
public class GreatSwordItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private BakedModel guiModel = null;
    private BakedModel handModel = null;

    private final ModelResourceLocation guiLocation = new ModelResourceLocation(new Identifier(Artisanal.MOD_ID, "item/great_sword_inventory"), "inventory");
    private final ModelResourceLocation handLocation = new ModelResourceLocation(new Identifier(Artisanal.MOD_ID, "item/great_sword_handheld"), "inventory");

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
}
