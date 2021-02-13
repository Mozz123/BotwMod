package botwmod.client.render.entity.projectile;

import botwmod.entity.projectile.BombEntity;
import botwmod.entity.projectile.MasterSwordBeamEntity;
import botwmod.registry.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class BombEntityRender<T extends BombEntity> extends EntityRenderer<T> {
    private ItemStack TEXTURE = new ItemStack(ModItems.BOMB.get());
    private static final float SCALE = 0.8F;

    public BombEntityRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(BombEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) + 0.0F));
        matrixStackIn.translate(0, 0.5F, 0);
        matrixStackIn.scale(2F, 2F, 2F);
        matrixStackIn.translate(0, -0.15F, 0);
        Minecraft.getInstance().getItemRenderer().renderItem(TEXTURE, ItemCameraTransforms.TransformType.GROUND, 240, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}