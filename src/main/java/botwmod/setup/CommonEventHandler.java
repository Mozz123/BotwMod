package botwmod.setup;

import botwmod.BotwMod;
import botwmod.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
}