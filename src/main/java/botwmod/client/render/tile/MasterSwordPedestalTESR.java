package botwmod.client.render.tile;

import botwmod.blocks.tile.MasterSwordPedestalTile;
import botwmod.registry.ModItems;
import botwmod.registry.ModTiles;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType.FIXED;

@OnlyIn(Dist.CLIENT)
public class MasterSwordPedestalTESR  extends TileEntityRenderer<MasterSwordPedestalTile> {
    public MasterSwordPedestalTESR(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(MasterSwordPedestalTile tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemStack sword = tileEntity.getSwordInPedestal();
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        matrixStack.push();
        matrixStack.translate(0.5D, 1D, 0.5D);
        matrixStack.scale(1F, 1F, 1F);

        renderer.renderItem(sword, FIXED, combinedLight, combinedOverlay, matrixStack, buffer);
        matrixStack.pop();
    }

    @Override
    public boolean isGlobalRenderer(MasterSwordPedestalTile tileEntity) {
        return true;
    }

    public static void register() {
        ClientRegistry.bindTileEntityRenderer(ModTiles.MASTER_SWORD_PEDESTAL.get(), MasterSwordPedestalTESR::new);
    }
}