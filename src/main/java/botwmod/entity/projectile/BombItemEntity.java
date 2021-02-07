package botwmod.entity.projectile;

import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BombItemEntity extends ProjectileItemEntity {
    public int radius = 5;
    public static double explosionStrength = 1.9;
    public static boolean explosionsBreakBlocks = false;

    public BombItemEntity(EntityType<? extends BombItemEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public BombItemEntity(World worldIn, LivingEntity throwerIn) {
        super(ModEntities.BOMB.get(), throwerIn, worldIn);
    }

    public BombItemEntity(World worldIn, double x, double y, double z) {
        super(ModEntities.BOMB.get(), x, y, z, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return ModItems.BOMB.get();
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        if (!world.isRemote) {
            world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float) explosionStrength, false, explosionsBreakBlocks ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.ticksExisted < 10) {
            return;
        }
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }
}