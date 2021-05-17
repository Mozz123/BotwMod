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

        matrixStack.push();

        matrixStack.translate(0.5F, 1.5F, 0.5F);
        matrixStack.scale(1F, 1F, 1F);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(180f));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(-45f));
        if (blockstate.get(SwordPedestalBlock.SHOULD_ANIMATION_START) && tileEntity.stillTicks <= 0) {
            matrixStack.translate((tileEntity.animationTicks+partialTicks)*0.2F, -(tileEntity.animationTicks+partialTicks)*0.2F, 0);
        } else if (tileEntity.stillTicks > 0) {
            matrixStack.translate(2.2F, -2.2F, 0);
        }
        renderer.renderItem(sword, FIXED, 240, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
        matrixStack.pop();
    }

    @Override
    public boolean isGlobalRenderer(SwordPedestalTile tileEntity) {
        return true;
    }
}