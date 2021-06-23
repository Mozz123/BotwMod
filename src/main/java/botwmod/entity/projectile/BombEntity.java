package botwmod.entity.projectile;

import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BombEntity extends ProjectileItemEntity {
    public static double explosionStrength = 1.9;

    public BombEntity(EntityType<? extends BombEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public BombEntity(World worldIn, LivingEntity throwerIn) {
        super(ModEntities.BOMB.get(), throwerIn, worldIn);
    }

    public BombEntity(World worldIn, double x, double y, double z) {
        super(ModEntities.BOMB.get(), x, y, z, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return ModItems.BOMB.get();
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Explosion.Mode.BREAK);
            this.remove();
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}