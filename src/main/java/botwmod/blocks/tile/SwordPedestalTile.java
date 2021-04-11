package botwmod.blocks.tile;

import botwmod.registry.ModTiles;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class SwordPedestalTile extends TileEntity implements ITickableTileEntity {
    private ItemStack swordInPedestal;
    public boolean startMasterSwordAnimation;
    public int timeSinceAnimationStart;

    public SwordPedestalTile() {
        super(ModTiles.SWORD_PEDESTAL.get());
        this.swordInPedestal = ItemStack.EMPTY;
        this.startMasterSwordAnimation = false;
        this.timeSinceAnimationStart = 0;
    }

    @Override
    public void tick() {
        if (startMasterSwordAnimation) {

        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.loadFromNBT(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        this.saveToNbt(compound);
        return compound;
    }

    public CompoundNBT saveToNbt(CompoundNBT compound) {
        compound.put("sword_in_pedestal", this.swordInPedestal.write(new CompoundNBT()));
        return compound;
    }

    public void loadFromNBT(CompoundNBT compound) {
        if (compound.contains("sword_in_pedestal", Constants.NBT.TAG_COMPOUND)) {
            this.swordInPedestal = ItemStack.read(compound.getCompound("sword_in_pedestal"));
        }
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 12, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        return super.receiveClientEvent(id, type);
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public BlockPos getPos() {
        return this.pos;
    }

    public ItemStack getSwordInPedestal() {
        return this.swordInPedestal;
    }

    public void setSwordInPedestal(ItemStack stack) {
        this.swordInPedestal = stack;
        this.markDirty();
        this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}