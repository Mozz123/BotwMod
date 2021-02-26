package botwmod.client.render.entity.layer;

import botwmod.BotwMod;
import botwmod.registry.ModEffects;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public class FrozenEffectLayer extends LayerRenderer<LivingEntity, EntityModel<LivingEntity>> {
    private static final RenderType TEXTURE = RenderType.getEntitySmoothCutout(new ResourceLocation("botwmod:textures/entity/frozen_layer.png"));
    private final LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer;

    public FrozenEffectLayer(LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer) {
        super(renderer);
        this.renderer = renderer;
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer buffer, int packedLightIn, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
        if (entity.isPotionActive(ModEffects.FROZEN_EFFECT.get())) {
            IVertexBuilder ivertexbuilder = buffer.getBuffer(TEXTURE);
            this.getEntityModel().render(matrix, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        }
    }
}