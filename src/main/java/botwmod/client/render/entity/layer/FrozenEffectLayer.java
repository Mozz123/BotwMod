package botwmod.client.render.entity.layer;

import botwmod.BotwMod;
import botwmod.registry.ModEffects;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class FrozenEffectLayer<T extends LivingEntity> extends LayerRenderer<T, EntityModel<T>> {
    private static final ResourceLocation FROZEN_TEXTURE = new ResourceLocation(BotwMod.MODID, "textures/entity/frozen_overlay.png");
    private final IEntityRenderer<T, EntityModel<T>> renderer;

    public FrozenEffectLayer(IEntityRenderer<T, EntityModel<T>> renderer) {
        super(renderer);
        this.renderer = renderer;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (living.isPotionActive(ModEffects.FROZEN_EFFECT.get())) {
            EntityModel model = this.renderer.getEntityModel();
            float transparency = 1;
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(FROZEN_TEXTURE));
            model.render(matrixStackIn, ivertexbuilder, packedLightIn, 0, 1, 1, 1, transparency);
        }
    }

}