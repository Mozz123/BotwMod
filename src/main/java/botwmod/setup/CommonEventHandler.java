package botwmod.setup;

import botwmod.BotwMod;
import botwmod.registry.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
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
    public static void masterSwordCooldown(PlayerInteractEvent eventIn) {
        PlayerEntity playerEntity = eventIn.getPlayer();
        ItemStack itemStack = eventIn.getItemStack();
        int durabilityLeft = itemStack.getMaxDamage() - itemStack.getDamage();

        if (itemStack.getItem() == ModItems.MASTER_SWORD.get() && durabilityLeft == 2) {
                (playerEntity).getCooldownTracker().setCooldown(itemStack.getItem(), 18000);
                itemStack.setDamage(0);
        }
    }
}