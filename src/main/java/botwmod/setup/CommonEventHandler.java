package botwmod.setup;

import botwmod.BotwMod;
import botwmod.entity.projectile.MasterSwordBeamEntity;
import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
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
    public static void masterSwordCooldownAttack(LivingHurtEvent event) {
        Entity source = event.getSource().getTrueSource();
        float damage = event.getAmount();

        if (source instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source;
            Item item = player.getHeldItemMainhand().getItem();
            if (item == ModItems.MASTER_SWORD.get() && player.getCooldownTracker().hasCooldown(item)) {
                event.setAmount(1);
            } else event.setAmount(damage);
        }
    }

    @SubscribeEvent
    public static void masterSwordBeamAttack(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        PlayerEntity player = event.getPlayer();
        if (stack.getItem() == ModItems.MASTER_SWORD.get()) {
            Multimap<Attribute, AttributeModifier> dmg = stack.getAttributeModifiers(EquipmentSlotType.MAINHAND);
            double totalDmg = 0;
            for (AttributeModifier modifier : dmg.get(Attributes.ATTACK_DAMAGE)) {
                totalDmg += modifier.getAmount();
            }
            MasterSwordBeamEntity shot = new MasterSwordBeamEntity(ModEntities.MASTER_SWORD.get(), player.world, player, totalDmg * 0.5F);
            Vector3d vector3d = player.getLook(1.0F);
            Vector3f vector3f = new Vector3f(vector3d);
            shot.shoot(vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1.0F, 0.5F);
            player.world.addEntity(shot);
        }
    }
}