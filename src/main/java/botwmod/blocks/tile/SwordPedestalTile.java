package botwmod.blocks.tile;

import botwmod.blocks.SwordPedestalBlock;
import botwmod.registry.ModBlocks;
import botwmod.registry.ModTiles;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class SwordPedestalTile extends TileEntity implements ITickableTileEntity {
    private ItemStack swordInPedestal;
    public int animationTicks;
    public int stillTicks;

    public SwordPedestalTile() {
        super(ModTiles.SWORD_PEDESTAL.get());
        this.swordInPedestal = ItemStack.EMPTY;
        this.animationTicks = 0;
        this.stillTicks = 0;
    }

    @Override
    public void tick() {
        if (this.getBlockState().getValue(SwordPedestalBlock.SHOULD_ANIMATION_START)) {
            this.animationTicks++;
        } else {
            this.animationTicks = 0;
            this.stillTicks = 0;
        }
        if (this.animationTicks >= 5) {
            this.stillTicks++;
        }
        if (this.stillTicks >= 100) {
            this.getLevel().setBlockAndUpdate(this.getBlockPos(), ModBlocks.SWORD_PEDESTAL.get().defaultBlockState());
            this.animationTicks = 0;
            this.stillTicks = 0;
            BlockPos pos = this.getBlockPos();
            InventoryHelper.dropItemStack(this.getLevel(), (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), this.swordInPedestal.getStack());
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.loadFromNBT(nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        this.saveToNbt(compound);
        return compound;
    }

    public CompoundNBT saveToNbt(CompoundNBT compound) {
        compound.put("sword_in_pedestal", this.swordInPedestal.save(new CompoundNBT()));
        return compound;
    }

    public void loadFromNBT(CompoundNBT compound) {
        if (compound.contains("sword_in_pedestal", Constants.NBT.TAG_COMPOUND)) {
            this.swordInPedestal = ItemStack.of(compound.getCompound("sword_in_pedestal"));
        }
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), 12, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.getLevel().getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public double getViewDistance() {
        return 65536.0D;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        return super.triggerEvent(id, type);
    }

    @Override
    public World getLevel() {
        return this.level;
    }

    @Override
    public BlockPos getBlockPos() {
        return this.worldPosition;
    }

    public ItemStack getSwordInPedestal() {
        return this.swordInPedestal;
    }

    public void setSwordInPedestal(ItemStack stack) {
        this.swordInPedestal = stack;
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}