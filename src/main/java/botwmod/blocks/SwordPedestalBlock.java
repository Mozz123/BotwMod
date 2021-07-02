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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SwordPedestalBlock extends Block {
    public static final BooleanProperty SHOULD_ANIMATION_START = BooleanProperty.create("should_animation_start");
    public static final BooleanProperty NOT_ENOUGH_HP = BooleanProperty.create("not_enough_hp");

    public SwordPedestalBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHOULD_ANIMATION_START, Boolean.FALSE).setValue(NOT_ENOUGH_HP, Boolean.FALSE));
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof SwordPedestalTile) {
            tileEntity.clearRemoved();
            worldIn.setBlockEntity(pos, tileEntity);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (!world.isClientSide && tileEntity instanceof SwordPedestalTile) {
            SwordPedestalTile pedestalTileEntity = (SwordPedestalTile) tileEntity;
            if (player.isCrouching()) {
                if (player.getUsedItemHand() == Hand.MAIN_HAND && player.getMainHandItem().isEmpty()) {
                    final ItemStack swordInPedestal = pedestalTileEntity.getSwordInPedestal();
                    if (pedestalTileEntity.getSwordInPedestal().getItem() == ModItems.MASTER_SWORD.get()) {
                        if (player.getMaxHealth() >= 30 || player.isCreative()) {
                            world.setBlock(pos, state.setValue(SHOULD_ANIMATION_START, !state.getValue(SHOULD_ANIMATION_START)), 3);
                        } else {
                            player.displayClientMessage(new TranslationTextComponent("botwmod.pedestal.lowhp"), true);
                        }
                    } else {
                        pedestalTileEntity.setSwordInPedestal(ItemStack.EMPTY);
                        player.drop(swordInPedestal, false);
                    }
                }
            } else {
                ItemStack stack = player.getItemInHand(hand);
                boolean isSword = stack.getItem() instanceof SwordItem;

                if (hand == Hand.MAIN_HAND) {
                    boolean isPedestalEmpty = pedestalTileEntity.getSwordInPedestal().isEmpty();
                    if (isPedestalEmpty && isSword) {
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
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof SwordPedestalTile) {
                InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(),
                        ((SwordPedestalTile) tileentity).getSwordInPedestal().copy()
                );
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(SHOULD_ANIMATION_START, Boolean.FALSE).setValue(NOT_ENOUGH_HP, Boolean.FALSE);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SHOULD_ANIMATION_START).add(NOT_ENOUGH_HP);
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