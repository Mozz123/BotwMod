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
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class ShockArrowEntity extends AbstractArrowEntity {
    public ShockArrowEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setBaseDamage(2.5F);
    }

    public ShockArrowEntity(EntityType type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPos(x, y, z);
        this.setBaseDamage(2.5F);
    }

    public ShockArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModEntities.SHOCK_ARROW.get(), world);
    }

    public ShockArrowEntity(EntityType type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setBaseDamage(2.5F);
    }

    public void tick() {
        super.tick();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        EffectInstance effectinstance = new EffectInstance(ModEffects.PARALYZED_EFFECT.get(), 40, 1);
        living.addEffect(effectinstance);

        if (living.isInWaterOrRain()) {
            this.setBaseDamage(this.getBaseDamage() * 2);
        }

        ItemStack mainhandstack = living.getItemBySlot(EquipmentSlotType.MAINHAND);
        ItemStack offhandstack = living.getItemBySlot(EquipmentSlotType.OFFHAND);
        if (living.hasItemInSlot(EquipmentSlotType.MAINHAND)) {
            living.spawnAtLocation(mainhandstack);
            living.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        }
        if (living.hasItemInSlot(EquipmentSlotType.OFFHAND)) {
            living.spawnAtLocation(offhandstack);
            living.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        }

        if (living.isInWaterOrRain() && living.isInWater()) {
            double radius = 10;
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(this.getOnPos()).inflate(radius).expandTowards(0.0D, this.level.getHeight(), 0.0D);
            List<Entity> getEntitiesInBB = level.getEntitiesOfClass(Entity.class, axisalignedbb);
            for (Entity entity : getEntitiesInBB) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingInBB = (LivingEntity) entity;
                    if (livingInBB.isInWater()) {
                        EffectInstance effectinstanceInBB = new EffectInstance(ModEffects.PARALYZED_EFFECT.get(), 60, 1);
                        livingInBB.addEffect(effectinstanceInBB);
                    }
                }
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.SHOCK_ARROW.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}