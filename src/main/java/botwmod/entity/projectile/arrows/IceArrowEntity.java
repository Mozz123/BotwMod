package botwmod.entity.projectile.arrows;

import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class IceArrowEntity extends AbstractArrowEntity {
    public IceArrowEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setDamage(2.5F);
    }

    public IceArrowEntity(EntityType type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        this.setDamage(2.5F);
    }

    public IceArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.ICE_ARROW.get(), world);
    }

    public IceArrowEntity(EntityType type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setDamage(2.5F);
    }


    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.ICE_ARROW.get());
    }

    public void tick() {
        super.tick();
        if (!this.world.isRemote() && this.inGround && this.timeInGround != 0 && this.timeInGround >= 600) {
            this.world.setEntityState(this, (byte)0);
        }

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}