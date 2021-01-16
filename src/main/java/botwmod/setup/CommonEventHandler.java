package botwmod.setup;

import botwmod.BotwMod;
import botwmod.entity.projectile.MasterSwordBeamEntity;
import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod.EventBusSubscriber(modid = BotwMod.MODID)
@ParametersAreNonnullByDefault
public class CommonEventHandler {

    public static void init(final FMLCommonSetupEvent eventIn) {
    }

    @SubscribeEvent
    public static void masterSwordAttack(LivingHurtEvent event) {
        Entity source = event.getSource().getTrueSource();
        float damage = event.getAmount();

        if (source instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source;
            World world = player.world;
            Item item = player.getHeldItemMainhand().getItem();

            if (item == ModItems.MASTER_SWORD.get() && player.getCooldownTracker().hasCooldown(item)) {
                event.setAmount(1);
            } else event.setAmount(damage);
        }
    }

    public static void onLeftClick(PlayerEntity living, ItemStack stack) {
        if (stack.getItem() == ModItems.MASTER_SWORD.get()) {
                Multimap<Attribute, AttributeModifier> dmg = stack.getAttributeModifiers(EquipmentSlotType.MAINHAND);
                double totalDmg = 0;
                for (AttributeModifier modifier : dmg.get(Attributes.ATTACK_DAMAGE)) {
                    totalDmg += modifier.getAmount();
                }
                living.playSound(SoundEvents.ENTITY_ZOMBIE_INFECT, 1, 1);
                MasterSwordBeamEntity shot = new MasterSwordBeamEntity(ModEntities.MASTER_SWORD.get(), living.world, living, totalDmg * 0.5F);
                Vector3d vector3d = living.getLook(1.0F);
                Vector3f vector3f = new Vector3f(vector3d);
                shot.shoot(vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1.0F, 0.5F);
                living.world.addEntity(shot);
        }
    }


    static int ticksPast = 0;

    @SubscribeEvent
    public static void awakeningTickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = event.player.world;
        ItemStack stack = player.getHeldItemMainhand();

        if (ticksPast == 5) {
            if (!world.getEntitiesWithinAABB(ZombieEntity.class, new AxisAlignedBB(player.getPosX() + -4, player.getPosY() + -4, player.getPosZ() + -4, player.getPosX() + 4, player.getPosY() + 4, player.getPosZ() + 4)).isEmpty()) {
                if (stack.getItem() == ModItems.MASTER_SWORD.get()) {
                    int swordSlot = player.inventory.getSlotFor(new ItemStack(ModItems.MASTER_SWORD.get()));
                    player.inventory.getCurrentItem().shrink(1);

                    player.inventory.add(swordSlot, new ItemStack(ModItems.AMBER.get()));
                }
            }
            ticksPast = 0;
        } else ticksPast++;
    }
}