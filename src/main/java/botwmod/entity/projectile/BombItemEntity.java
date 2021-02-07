package botwmod.entity.projectile;

import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.entity.Entity;
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
        super.onImpact(result);
        if (!this.world.isRemote) {
            world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float) explosionStrength, false, explosionsBreakBlocks ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
            this.remove();
        }
    }
}