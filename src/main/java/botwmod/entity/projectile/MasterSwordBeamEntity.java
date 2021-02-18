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
        this.setDamage(10F);
    }

    public MasterSwordBeamEntity(EntityType type, World worldIn, double x, double y, double z, float r, float g, float b) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        this.setDamage(10F);
    }

    public MasterSwordBeamEntity(EntityType type, World worldIn, LivingEntity shooter, double dmg) {
        super(type, shooter, worldIn);
        this.setDamage(dmg);
    }

    public MasterSwordBeamEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this(ModEntities.MASTER_SWORD.get(), worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        Entity thrower = func_234616_v_();
        double d0 = 0;
        double d1 = 0.0D;
        double d2 = 0.01D;
        double x = this.getPosX() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth();
        double y = this.getPosY() + (double) (this.rand.nextFloat() * this.getHeight()) - (double) this.getHeight();
        double z = this.getPosZ() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth();
        float f = (this.getWidth() + this.getHeight() + this.getWidth()) * 0.333F + 0.5F;
        if (particleDistSq(x, y, z) < f * f) {
            this.world.addParticle(ParticleTypes.SNEEZE, x, y + 0.5D, z, d0, d1, d2);
        }
        if (this.ticksExisted > lifespan) {
            remove();
            return;
        }
        if (thrower instanceof net.minecraft.entity.player.PlayerEntity && !thrower.isAlive()) {
            remove();
            return;
        }

        this.func_234617_x_();
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        Entity entity = p_213868_1_.getEntity();
        entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.func_234616_v_()), (float) this.getDamage());
    }

    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    public double particleDistSq(double toX, double toY, double toZ) {
        double d0 = getPosX() - toX;
        double d1 = getPosY() - toY;
        double d2 = getPosZ() - toZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    @Override
    protected void onImpact(RayTraceResult raytrace) {
        super.onImpact(raytrace);
        if (this.isAlive()) {
            remove();
        }
    }

    @Override
    public boolean hasNoGravity() {
        if (this.isInWater()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isPushedByWater() {
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
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}