package botwmod.entity.projectile.arrows;

import botwmod.registry.ModEffects;
import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class ShockArrowEntity extends AbstractArrowEntity {
    public ShockArrowEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setDamage(2.5F);
    }

    public ShockArrowEntity(EntityType type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        this.setDamage(2.5F);
    }

    public ShockArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.SHOCK_ARROW.get(), world);
    }

    public ShockArrowEntity(EntityType type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setDamage(2.5F);
    }

    public void tick() {
        super.tick();
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        EffectInstance effectinstance = new EffectInstance(ModEffects.PARALYZED_EFFECT.get(), 40, 1);
        living.addPotionEffect(effectinstance);

        if (living.isWet()) {
            this.setDamage(this.getDamage() * 2);
        }

        ItemStack mainhandstack = living.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        ItemStack offhandstack = living.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
        if (living.hasItemInSlot(EquipmentSlotType.MAINHAND)) {
            living.entityDropItem(mainhandstack);
            living.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        }
        if (living.hasItemInSlot(EquipmentSlotType.OFFHAND)) {
            living.entityDropItem(offhandstack);
            living.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        }

        if (living.isWet() && living.isInWater()) {
            double radius = 10;
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(this.getOnPosition()).grow(radius).expand(0.0D, this.world.getHeight(), 0.0D);
            List<Entity> getEntitiesInBB = world.getEntitiesWithinAABB(Entity.class, axisalignedbb);
            for (Entity entity : getEntitiesInBB) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingInBB = (LivingEntity) entity;
                    if (livingInBB.isInWater()) {
                        EffectInstance effectinstanceInBB = new EffectInstance(ModEffects.PARALYZED_EFFECT.get(), 60, 1);
                        livingInBB.addPotionEffect(effectinstanceInBB);
                    }
                }
            }
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.SHOCK_ARROW.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}