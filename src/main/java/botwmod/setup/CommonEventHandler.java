package botwmod.setup;

import botwmod.BotwMod;
import botwmod.entity.projectile.MasterSwordBeamEntity;
import botwmod.registry.ModEffects;
import botwmod.registry.ModEntities;
import botwmod.registry.ModItems;
import botwmod.registry.ModProfessions;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

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

    public static void onRightClick(PlayerEntity living, ItemStack stack) {
        if (stack.getItem() == ModItems.MASTER_SWORD.get() || stack.getItem() == ModItems.MASTER_SWORD_AWAKENED.get()) {
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
            stack.damageItem(2, living, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {

        if (event.getType() != ModProfessions.JEWELER.get())
            return;

        Int2ObjectMap<List<VillagerTrades.ITrade>> trademap = event.getTrades();

        trademap.get(1).addAll(Arrays.asList(
                sellItem(new ItemStack(ModItems.AMBER.get(), 5), 30, 8, 1, 2),
                sellItem(new ItemStack(ModItems.OPAL.get(), 5), 30, 8, 1, 2)
        ));

        trademap.get(2).addAll(Arrays.asList(
                sellItem(new ItemStack(ModItems.TOPAZ.get(), 4), 30, 6, 2, 2),
                sellItem(new ItemStack(ModItems.RUBY.get(), 4), 30, 6, 2, 2)
        ));

        trademap.get(3).addAll(Arrays.asList(
                sellItem(new ItemStack(ModItems.SAPPHIRE.get(), 3), 30, 4, 1, 2)
        ));
    }

    private static VillagerTrades.ITrade sellItem(IItemProvider thing, int price, int maxTrades, int xp, float priceMultiplier) {
        return sellItem(new ItemStack(thing), price, maxTrades, xp, priceMultiplier);
    }

    private static VillagerTrades.ITrade sellItem(ItemStack thing, int price, int maxTrades, int xp, float priceMultiplier) {
        return new BasicTrade(new ItemStack(Items.EMERALD, price), thing, maxTrades, xp, priceMultiplier);
    }

    private static VillagerTrades.ITrade buyItem(ItemStack thing, int reward, int maxTrades, int xp, float priceMultiplier) {
        return new BasicTrade(thing, new ItemStack(Items.EMERALD, reward), maxTrades, xp, priceMultiplier);
    }


    private static int ticksPast = 0;

    @SubscribeEvent
    public static void awakeningTickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = event.player.world;
        ItemStack stack = player.getHeldItemMainhand();

        if (ticksPast == 5) {
            boolean entityInArea = world.getEntitiesWithinAABB(ZombieEntity.class, new AxisAlignedBB(player.getPosX() + -4, player.getPosY() + -4, player.getPosZ() + -4, player.getPosX() + 4, player.getPosY() + 4, player.getPosZ() + 4)).isEmpty();

            if (!entityInArea) {
                if (stack.getItem() == ModItems.MASTER_SWORD.get()) {
                    int normalSwordSlot = player.inventory.getSlotFor(new ItemStack(ModItems.MASTER_SWORD.get()));
                    player.inventory.getCurrentItem().shrink(1);

                    player.inventory.add(normalSwordSlot, new ItemStack(ModItems.MASTER_SWORD_AWAKENED.get()));
                }
            } else if (stack.getItem() == ModItems.MASTER_SWORD_AWAKENED.get()) {
                int awakenedSwordSlot = player.inventory.getSlotFor(new ItemStack(ModItems.MASTER_SWORD_AWAKENED.get()));
                player.inventory.getCurrentItem().shrink(1);

                player.inventory.add(awakenedSwordSlot, new ItemStack(ModItems.MASTER_SWORD.get()));
            }
            ticksPast = 0;
        } else ticksPast++;
    }
}