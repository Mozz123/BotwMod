package botwmod.entity.projectile;

import botwmod.registry.ModEntities;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class MasterSwordBeamEntity extends AbstractArrowEntity {

    protected int lifespan = 300;

    public MasterSwordBeamEntity (EntityType type, World world) {
        super(type, world);
        this.setDamage(5F);
    }

    public MasterSwordBeamEntity(EntityType type, World worldIn, double x, double y, double z, float r, float g, float b) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        this.setDamage(5F);
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
        Entity thrower = func_234616_v_();
        if (thrower instanceof net.minecraft.entity.player.PlayerEntity && !thrower.isAlive()) {
            remove();
            return;
        }
        // remove if too old
        if(this.ticksExisted > lifespan) {
            remove();
            return;
        }
        // check for impact
        if(!this.getEntityWorld().isRemote()) {
            RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
            if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS
                    && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onImpact(raytraceresult);
            }
        }
        // movement
        Vector3d motion = this.getMotion();
        double d0 = this.getPosX() + motion.x;
        double d1 = this.getPosY() + motion.y;
        double d2 = this.getPosZ() + motion.z;
        // lerp rotation and pitch
        this.func_234617_x_();
        // actually move the entity
        this.setPosition(d0, d1, d2);
        // super method
        super.tick();
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