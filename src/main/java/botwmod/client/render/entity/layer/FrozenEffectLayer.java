package botwmod.client.render.entity.layer;

import botwmod.BotwMod;
import botwmod.registry.ModEffects;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public class FrozenEffectLayer extends LayerRenderer<LivingEntity, EntityModel<LivingEntity>> {
    private static final ResourceLocation FROZEN_LAYER_TEXTURE = new ResourceLocation(BotwMod.MODID, "textures/entity/frozen_layer.png");
    private final LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer;

    public FrozenEffectLayer(LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer) {
        super(renderer);
        this.renderer = renderer;
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer buffer, int packedLightIn, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
        if (entity.isPotionActive(ModEffects.FROZEN_EFFECT.get())) {
            EntityModel model = this.getEntityModel();
            IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.getEntityTranslucent(FROZEN_LAYER_TEXTURE));

            model.setRotationAngles(entity, limbSwing, limbSwingAmount, age, netHeadYaw, headPitch);
            model.render(matrix, ivertexbuilder, packedLightIn, 0, 1, 1, 1, 1);

        }
    }
}