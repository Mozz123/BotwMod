package botwmod.entity.projectile.arrows;

import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireArrowEntity extends AbstractArrowEntity {
    public FireArrowEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setBaseDamage(2.5F);
    }

    public FireArrowEntity(EntityType type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPos(x, y, z);
        this.setBaseDamage(2.5F);
    }

    public FireArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.FIRE_ARROW.get(), world);
    }

    public FireArrowEntity(EntityType type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setBaseDamage(2.5F);
    }

    public void tick() {
        super.tick();

        if (this.isInWaterOrRain()) {
            this.remove();
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        living.setRemainingFireTicks(600);
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
                TNTBlock tntBlock = new TNTBlock(AbstractBlock.Properties.of(Material.EXPLOSIVE).instabreak().sound(SoundType.GRASS));

                if (level.getBlockState(pos2).getBlock() == Blocks.AIR && level.getBlockState(pos1).getBlock() == Blocks.AIR
                        && level.getBlockState(pos).getBlock() != Blocks.ICE && level.getBlockState(pos).getBlock() != Blocks.PACKED_ICE
                        && level.getBlockState(pos).getBlock() != Blocks.BLUE_ICE && level.getBlockState(pos).getBlock() != Blocks.TNT) {
                    level.setBlockAndUpdate(pos1, Blocks.FIRE.defaultBlockState());
                } else if (level.getBlockState(pos).getBlock() == Blocks.ICE) {
                    level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                } else if (level.getBlockState(pos).getBlock() == Blocks.PACKED_ICE) {
                    level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                } else if (level.getBlockState(pos).getBlock() == Blocks.BLUE_ICE) {
                    level.setBlockAndUpdate(pos, Blocks.PACKED_ICE.defaultBlockState());
                } else if (level.getBlockState(pos).getBlock() == Blocks.TNT) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    tntBlock.catchFire(level.getBlockState(pos), level, pos, null, null);
                }
            }
        }
        this.remove();
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.FIRE_ARROW.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}