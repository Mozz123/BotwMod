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
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {

            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.func_234616_v_());
            this.world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), (float) this.explosionStrength, flag, flag ? Explosion.Mode.NONE : Explosion.Mode.NONE);
            this.remove();
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}