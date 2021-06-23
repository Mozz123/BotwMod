package botwmod.entity.projectile.arrows;

import botwmod.registry.ModBlocks;
import botwmod.registry.ModEffects;
import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class IceArrowEntity extends AbstractArrowEntity {
    public IceArrowEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setBaseDamage(2.5F);
    }

    public IceArrowEntity(EntityType type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPos(x, y, z);
        this.setBaseDamage(2.5F);
    }

    public IceArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.ICE_ARROW.get(), world);
    }

    public IceArrowEntity(EntityType type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setBaseDamage(2.5F);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        EffectInstance effectinstance = new EffectInstance(ModEffects.FROZEN_EFFECT.get(), 600, 1);
        living.addEffect(effectinstance);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            RayTraceResult.Type result$type = result.getType();
            if (result$type == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult target = (BlockRayTraceResult) result;
                BlockPos pos = new BlockPos(target.getBlockPos().getX(), target.getBlockPos().getY(), target.getBlockPos().getZ());
                BlockPos pos1 = new BlockPos(target.getBlockPos().getX(), target.getBlockPos().getY()+1, target.getBlockPos().getZ());
                BlockPos pos2 = new BlockPos(target.getBlockPos().getX(), target.getBlockPos().getY()+2, target.getBlockPos().getZ());

                if (level.getBlockState(pos2).getBlock() == Blocks.AIR && level.getBlockState(pos1).getBlock() == Blocks.AIR) {
                    level.setBlockAndUpdate(pos1, ModBlocks.FROZEN_SPIKES.get().defaultBlockState());
                }
            }
        }
        this.remove();
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        BlockState pos = level.getBlockState(this.blockPosition());
        if (!this.hasImpulse) {
            if(pos == Blocks.WATER.defaultBlockState()) {
                BlockPos currentPos = this.blockPosition();
                level.setBlockAndUpdate(this.getOnPos(), Blocks.ICE.defaultBlockState());
                this.remove();
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.ICE_ARROW.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}