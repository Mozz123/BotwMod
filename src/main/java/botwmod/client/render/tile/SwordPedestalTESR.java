package botwmod.client.render.tile;

import botwmod.blocks.tile.SwordPedestalTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

        matrixStack.push();
        matrixStack.translate(0.5D, 1.5D, 0.5D);
        matrixStack.scale(1F, 1F, 1F);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(180f));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(-45f));

        renderer.renderItem(sword, FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
        matrixStack.pop();
    }

    @Override
    public boolean isGlobalRenderer(SwordPedestalTile tileEntity) {
        return true;
    }

    public static void register() {

    }
}