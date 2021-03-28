package botwmod.entity.projectile.arrows;

import botwmod.registry.ModBlocks;
import botwmod.registry.ModEffects;
import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireArrowEntity extends AbstractArrowEntity {
    public FireArrowEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setDamage(2.5F);
    }

    public FireArrowEntity(EntityType type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        this.setDamage(2.5F);
    }

    public FireArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.FIRE_ARROW.get(), world);
    }

    public FireArrowEntity(EntityType type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setDamage(2.5F);
    }

    public void tick() {
        super.tick();

        if (this.isWet()) {
            this.remove();
        }
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        living.setFire(600);
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
                TNTBlock tntBlock = new TNTBlock(AbstractBlock.Properties.create(Material.TNT).zeroHardnessAndResistance().sound(SoundType.PLANT));

                if (world.getBlockState(pos2).getBlock() == Blocks.AIR && world.getBlockState(pos1).getBlock() == Blocks.AIR
                        && world.getBlockState(pos).getBlock() != Blocks.ICE && world.getBlockState(pos).getBlock() != Blocks.PACKED_ICE
                        && world.getBlockState(pos).getBlock() != Blocks.BLUE_ICE && world.getBlockState(pos).getBlock() != Blocks.TNT) {
                    world.setBlockState(pos1, Blocks.FIRE.getDefaultState());
                } else if (world.getBlockState(pos).getBlock() == Blocks.ICE) {
                    world.setBlockState(pos, Blocks.WATER.getDefaultState());
                } else if (world.getBlockState(pos).getBlock() == Blocks.PACKED_ICE) {
                    world.setBlockState(pos, Blocks.ICE.getDefaultState());
                } else if (world.getBlockState(pos).getBlock() == Blocks.BLUE_ICE) {
                    world.setBlockState(pos, Blocks.PACKED_ICE.getDefaultState());
                } else if (world.getBlockState(pos).getBlock() == Blocks.TNT) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    tntBlock.catchFire(world.getBlockState(pos), world, pos, null, null);
                }
            }
        }
        this.remove();
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.FIRE_ARROW.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}