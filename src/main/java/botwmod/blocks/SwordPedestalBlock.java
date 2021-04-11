package botwmod.blocks;

import botwmod.blocks.tile.SwordPedestalTile;
import botwmod.registry.ModItems;
import botwmod.registry.ModTiles;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SwordPedestalBlock extends Block {
    public SwordPedestalBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof SwordPedestalTile) {
            tileEntity.validate();
            worldIn.setTileEntity(pos, tileEntity);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (!world.isRemote && tileEntity instanceof SwordPedestalTile) {
            SwordPedestalTile pedestalTileEntity = (SwordPedestalTile) tileEntity;
            if (player.isSneaking()) {
                if (player.getActiveHand() == Hand.MAIN_HAND && player.getHeldItemMainhand().isEmpty()) {
                    final ItemStack toDrop = pedestalTileEntity.getSwordInPedestal().copy();
                    if (toDrop != ModItems.MASTER_SWORD.get().getDefaultInstance()) {
                        pedestalTileEntity.setSwordInPedestal(ItemStack.EMPTY);
                        player.dropItem(toDrop, false);
                    }
                }
            } else {
                ItemStack stack = player.getHeldItem(hand);
                boolean isSword = stack.getItem() instanceof SwordItem;

                if (hand == Hand.MAIN_HAND) {
                    boolean isDisplayEmpty = pedestalTileEntity.getSwordInPedestal().isEmpty();
                    if (isDisplayEmpty && isSword) {
                        ItemStack copy = stack.copy();
                        pedestalTileEntity.setSwordInPedestal(copy);
                        stack.shrink(1);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof SwordPedestalTile) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(),
                        ((SwordPedestalTile) tileentity).getSwordInPedestal().copy()
                );
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTiles.SWORD_PEDESTAL.create();
    }
}