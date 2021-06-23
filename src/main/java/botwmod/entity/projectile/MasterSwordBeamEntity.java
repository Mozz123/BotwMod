package botwmod.entity.projectile;

import botwmod.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class MasterSwordBeamEntity extends AbstractArrowEntity {

    protected int lifespan = 160;

    public MasterSwordBeamEntity(EntityType type, World world) {
        super(type, world);
        this.setBaseDamage(10F);
    }

    public MasterSwordBeamEntity(EntityType type, World worldIn, double x, double y, double z, float r, float g, float b) {
        this(type, worldIn);
        this.setPos(x, y, z);
        this.setBaseDamage(10F);
    }

    public MasterSwordBeamEntity(EntityType type, World worldIn, LivingEntity shooter, double dmg) {
        super(type, shooter, worldIn);
        this.setBaseDamage(dmg);
    }

    public MasterSwordBeamEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this(ModEntities.MASTER_SWORD.get(), worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        Entity thrower = getOwner();
        if (this.tickCount > lifespan) {
            remove();
            return;
        }
        if (thrower instanceof net.minecraft.entity.player.PlayerEntity && !thrower.isAlive()) {
            remove();
            return;
        }

        this.updateRotation();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        Entity entity = p_213868_1_.getEntity();
        entity.hurt(DamageSource.indirectMagic(this, this.getOwner()), (float) this.getBaseDamage());
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    public double particleDistSq(double toX, double toY, double toZ) {
        double d0 = this.getX() - toX;
        double d1 = getY() - toY;
        double d2 = getZ() - toZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    @Override
    protected void onHit(RayTraceResult raytrace) {
        super.onHit(raytrace);
        if (this.isAlive()) {
            remove();
        }
    }

    @Override
    public boolean isNoGravity() {
        if (this.isInWater()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    public float getBrightness() {
        return 1.0F;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}