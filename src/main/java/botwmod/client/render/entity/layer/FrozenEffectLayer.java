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
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public class FrozenEffectLayer extends LayerRenderer<LivingEntity, EntityModel<LivingEntity>> {
    private static final ResourceLocation FROZEN_LAYER_TEXTURE = new ResourceLocation(BotwMod.MODID, "textures/entity/frozen_layer.png");

    private final LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer;
    private final Predicate<ModelRenderer> modelExclusions;

    public FrozenEffectLayer(LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer, Predicate<ModelRenderer> modelExclusions) {
        super(renderer);
        this.renderer = renderer;
        this.modelExclusions = modelExclusions;
    }

    public FrozenEffectLayer(LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer) {
        this(renderer, box -> {
            return false;
        });
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (living.isPotionActive(ModEffects.FROZEN_EFFECT.get())) {
            EntityModel model = this.renderer.getEntityModel();
            float transparency = 1;
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.color4f(1, 1, 1, transparency);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(FROZEN_LAYER_TEXTURE));
            model.render(matrixStackIn, ivertexbuilder, packedLightIn, 0, 1, 1, 1, 1);
            RenderSystem.color4f(1, 1, 1, 1);
            System.out.println("hiimme");
        }
    }
}