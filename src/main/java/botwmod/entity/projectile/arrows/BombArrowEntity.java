package botwmod.entity.projectile.arrows;

import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class BombArrowEntity extends AbstractArrowEntity {
    private double explosionStrength = 1.9;

    public BombArrowEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setDamage(2.5F);
    }

    public BombArrowEntity(EntityType type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        this.setDamage(2.5F);
    }

    public BombArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.BOMB_ARROW.get(), world);
    }

    public BombArrowEntity(EntityType type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setDamage(2.5F);
    }


    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.BOMB_ARROW.get());
    }

    public void tick() {
        super.tick();
        if (!this.world.isRemote() && this.inGround && this.timeInGround != 0 && this.timeInGround >= 600) {
            this.world.setEntityState(this, (byte) 0);
        }

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
