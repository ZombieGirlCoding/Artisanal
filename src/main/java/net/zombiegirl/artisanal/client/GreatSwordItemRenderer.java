package net.zombiegirl.artisanal.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static net.fabricmc.loader.impl.lib.mappingio.format.FeatureSet.MetadataSupport.FIXED;
import static net.minecraft.client.render.model.json.ModelTransformationMode.GROUND;
import static net.minecraft.client.render.model.json.ModelTransformationMode.GUI;

public class GreatSwordItemRenderer implements GreatSwordItemRendererInterface {
    private static final String MOD_ID = "artisanal";
    private static final ModelIdentifier GUI_MODEL_ID = new ModelIdentifier(new Identifier(MOD_ID, "great_sword_gui"), "inventory");
    private static final ModelIdentifier HANDHELD_MODEL_ID = new ModelIdentifier(new ModelIdentifier(MOD_ID, "great_sword"), "handheld");



    @Override
    public void render(ItemStack stack, ModelTransformation.Mode renderMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();

        // pick which model id to use
        ModelIdentifier chosenId;
        switch (renderMode) {
            case GUI:
            case GROUND:
            case FIXED:
                chosenId = GUI_MODEL_ID;
                break;
            default:
                // FIRST_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND, THIRD_PERSON_*, etc.
                chosenId = HANDHELD_MODEL_ID;
                break;
        }

        // obtain baked model
        BakedModel model = client.getBakedModelManager().getModel(chosenId);
        ItemRenderer itemRenderer = client.getItemRenderer();

        // Preferred 1.21-compatible call:
        // renderItem(ItemStack stack, ModelTransformation.Mode transformType, boolean leftHanded,
        //            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model)
        //   itemRenderer.renderItem(stack, renderMode, false, matrices, vertexConsumers, light, overlay, model);

        // If the above line does not compile in your mappings, try one of these alternatives (uncomment the one that matches):
        // Alternative A (some mappings/providers use renderBakedItemModel publicly):
        // itemRenderer.renderBakedItemModel(model, light, overlay, matrices, vertexConsumers);
        //
        // Alternative B (some mappings have a renderBakedItemModel accepting the stack):
        // itemRenderer.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumers);
        //
        // Alternative C (older/newer signatures):
        // itemRenderer.renderItem(stack, matrices, vertexConsumers, light, overlay, model); // example variant
        //
        // If you need help choosing which variant — paste the method signatures of ItemRenderer from your IDE/compiler error and I will tell you which one to enable.
    }

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {

    }
}

