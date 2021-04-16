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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SwordPedestalBlock extends Block {
    public static final BooleanProperty HAS_MASTER_SWORD = BooleanProperty.create("has_master_sword");

    public SwordPedestalBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HAS_MASTER_SWORD, Boolean.FALSE));
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
                    final ItemStack swordInPedestal = pedestalTileEntity.getSwordInPedestal();
                    if (pedestalTileEntity.getSwordInPedestal().getItem() == ModItems.MASTER_SWORD.get()) {
                        world.setBlockState(pos, state.with(HAS_MASTER_SWORD, !state.get(HAS_MASTER_SWORD)), 3);
                    } else {
                        pedestalTileEntity.setSwordInPedestal(ItemStack.EMPTY);
                        player.dropItem(swordInPedestal, false);
                    }
                }
            } else {
                ItemStack stack = player.getHeldItem(hand);
                boolean isSword = stack.getItem() instanceof SwordItem;

                if (hand == Hand.MAIN_HAND) {
                    boolean isDisplayEmpty = pedestalTileEntity.getSwordInPedestal().isEmpty();
                    if (isDisplayEmpty && isSword) {
                        ItemStack swordStack = stack.copy();
                        pedestalTileEntity.setSwordInPedestal(swordStack);
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HAS_MASTER_SWORD, Boolean.FALSE);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_MASTER_SWORD);
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