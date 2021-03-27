package botwmod.entity.projectile.arrows;

import botwmod.registry.ModBlocks;
import botwmod.registry.ModEffects;
import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class IceArrowEntity extends AbstractArrowEntity {
    public IceArrowEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setDamage(2.5F);
    }

    public IceArrowEntity(EntityType type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        this.setDamage(2.5F);
    }

    public IceArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.ICE_ARROW.get(), world);
    }

    public IceArrowEntity(EntityType type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setDamage(2.5F);
    }


    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.ICE_ARROW.get());
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        EffectInstance effectinstance = new EffectInstance(ModEffects.FROZEN_EFFECT.get(), 600, 1);
        living.addPotionEffect(effectinstance);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            RayTraceResult.Type result$type = result.getType();
            if (result$type == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult target = (BlockRayTraceResult) result;
                BlockPos pos = new BlockPos(target.getPos().getX(), target.getPos().getY(), target.getPos().getZ());
                BlockPos pos1 = new BlockPos(target.getPos().getX(), target.getPos().getY()+1, target.getPos().getZ());
                BlockPos pos2 = new BlockPos(target.getPos().getX(), target.getPos().getY()+2, target.getPos().getZ());

                if (world.getBlockState(pos2).getBlock() == Blocks.AIR && world.getBlockState(pos1).getBlock() == Blocks.AIR) {
                    world.setBlockState(pos1, ModBlocks.FROZEN_SPIKES.get().getDefaultState());
                }
            }
        }
        this.remove();
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        BlockState pos = world.getBlockState(getPosition());
        if (!this.isAirBorne) {
            if(pos == Blocks.WATER.getDefaultState()) {
                BlockPos currentPos = this.getPosition();
                world.setBlockState(this.getOnPosition(), Blocks.ICE.getDefaultState());
                this.remove();
            }
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}