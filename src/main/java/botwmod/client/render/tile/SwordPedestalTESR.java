package botwmod.client.render.tile;

import botwmod.blocks.SwordPedestalBlock;
import botwmod.blocks.tile.SwordPedestalTile;
import botwmod.registry.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType.FIXED;

@OnlyIn(Dist.CLIENT)
public class SwordPedestalTESR  extends TileEntityRenderer<SwordPedestalTile> {
    public SwordPedestalTESR(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(SwordPedestalTile tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemStack sword = tileEntity.getSwordInPedestal();
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        BlockState blockstate = tileEntity.getBlockState();

        matrixStack.pushPose();

        matrixStack.translate(0.5F, 1.5F, 0.5F);
        matrixStack.scale(1F, 1F, 1F);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(180f));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90f));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-45f));
        if (blockstate.getValue(SwordPedestalBlock.SHOULD_ANIMATION_START) && tileEntity.stillTicks == 0) {
            matrixStack.translate((tileEntity.animationTicks+partialTicks)*0.2F, -(tileEntity.animationTicks+partialTicks)*0.2F, 0);
        } else if (tileEntity.stillTicks > 0) {
            matrixStack.translate(1.1F, -1.1F, 0);
            beams(matrixStack, buffer, 0, 0, 0, tileEntity.stillTicks, partialTicks);
        }
        renderer.renderStatic(sword, FIXED, 240, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
        matrixStack.popPose();
    }

    private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0D) / 2.0D); // Got this from EnderDragonRenderer

    public static void beams(MatrixStack mStack, IRenderTypeBuffer buf, double x, double y, double z, int stillTicks, float partialTicks) {
        float f5 = ((float)stillTicks + partialTicks) / 400.0F;
        float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
        Random random = new Random(432L);
        IVertexBuilder iVertexBuilder = buf.getBuffer(RenderType.lightning());
        mStack.pushPose();
        mStack.translate(x, y, z);

        for(int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
            mStack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f5 * 90.0F));
            float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
            Matrix4f mat = mStack.last().pose();
            float alpha = 1 - f7;

            // Credit for this bit of code goes to Elucent

            iVertexBuilder.vertex(mat, 0.0F, 0.0F, 0.0F).color(53, 81, 98, alpha).endVertex();
            iVertexBuilder.vertex(mat, 0.0F, 0.0F, 0.0F).color(53, 81, 98, alpha).endVertex();
            iVertexBuilder.vertex(mat, -HALF_SQRT_3 * f4, f3, -0.5F * f4).color(53, 81, 98, 0).endVertex();
            iVertexBuilder.vertex(mat, HALF_SQRT_3 * f4, f3, -0.5F * f4).color(53, 81, 98, 0).endVertex();
            iVertexBuilder.vertex(mat, 0.0F, 0.0F, 0.0F).color(53, 81, 98, alpha).endVertex();
            iVertexBuilder.vertex(mat, 0.0F, 0.0F, 0.0F).color(53, 81, 98, alpha).endVertex();
            iVertexBuilder.vertex(mat, HALF_SQRT_3 * f4, f3, -0.5F * f4).color(53, 81, 98, 0).endVertex();
            iVertexBuilder.vertex(mat, 0.0F, f3, 1.0F * f4).color(53, 81, 98, 0).endVertex();
            iVertexBuilder.vertex(mat, 0.0F, 0.0F, 0.0F).color(53, 81, 98, alpha).endVertex();
            iVertexBuilder.vertex(mat, 0.0F, 0.0F, 0.0F).color(53, 81, 98, alpha).endVertex();
            iVertexBuilder.vertex(mat, 0.0F, f3, 1.0F * f4).color(53, 81, 98, 0).endVertex();
            iVertexBuilder.vertex(mat, -HALF_SQRT_3 * f4, f3, -0.5F * f4).color(53, 81, 98, 0).endVertex();
        }

        mStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(SwordPedestalTile tileEntity) {
        return true;
    }
}