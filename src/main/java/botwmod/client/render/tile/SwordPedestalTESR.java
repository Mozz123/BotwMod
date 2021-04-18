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

        matrixStack.translate(0.5D, 1.5D, 0.5D);
        matrixStack.scale(1F, 1F, 1F);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(180f));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(-45f));
        if (blockstate.get(SwordPedestalBlock.HAS_MASTER_SWORD)) {
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.getLightning());

            matrixStack.push();

            matrixStack.translate(0, (double)(-tileEntity.getAnimationProgress(partialTicks) * 0.5F), 0);

            matrixStack.pop();
        }
        renderer.renderItem(sword, FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
        matrixStack.pop();
    }

    private static void lightBlueBeams(IVertexBuilder ivertexbuilder, Matrix4f matrix4f) {
        ivertexbuilder.pos(matrix4f, 0.0F, 0.0F, 0.0F).color(86, 237, 253, 1).endVertex();
    }

    @Override
    public boolean isGlobalRenderer(SwordPedestalTile tileEntity) {
        return true;
    }
}