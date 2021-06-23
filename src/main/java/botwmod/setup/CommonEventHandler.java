package botwmod.setup;

import botwmod.BotwMod;
import botwmod.blocks.tile.SwordPedestalTile;
import botwmod.entity.projectile.MasterSwordBeamEntity;
import botwmod.items.HeartContainerItem;
import botwmod.registry.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

    public static void init(final FMLCommonSetupEvent event) {

    }

    // Trade Events

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {

        if (event.getType() != ModProfessions.JEWELER.get())
            return;

        Int2ObjectMap<List<VillagerTrades.ITrade>> trademap = event.getTrades();

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

    // Master Sword Events

    @SubscribeEvent
    public static void masterSwordAttack(LivingHurtEvent event) {
        Entity source = event.getSource().getEntity();
        float damage = event.getAmount();

        if (source instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source;
            World world = player.level;
            Item item = player.getMainHandItem().getItem();

            if (item == ModItems.MASTER_SWORD.get() && player.getCooldowns().isOnCooldown(item)) {
                event.setAmount(1);
            } else event.setAmount(damage);
        }
    }

    public static void onRightClick(PlayerEntity living, ItemStack stack) {
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;
            if (player.getHealth() == player.getMaxHealth()) {
                if (stack.getItem() == ModItems.MASTER_SWORD.get() || stack.getItem() == ModItems.MASTER_SWORD_AWAKENED.get()) {
                    double totalDmg = 5;
                    living.playSound(SoundEvents.ZOMBIE_INFECT, 1, 1);
                    MasterSwordBeamEntity shot = new MasterSwordBeamEntity(ModEntities.MASTER_SWORD.get(), living.level, living, totalDmg);
                    Vector3d vector3d = living.getViewVector(1.0F);
                    Vector3f vector3f = new Vector3f(vector3d);
                    shot.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 1.0F, 0.5F);
                    living.level.addFreshEntity(shot);
                    stack.hurtAndBreak(1, living, (entity) -> {
                        entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
                    });
                }
            }
        }
    }

    private static int ticksPast = 0;

    @SubscribeEvent
    public static void awakeningTickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = event.player.level;
        ItemStack stack = player.getMainHandItem();

        if (ticksPast == 5) {
            boolean entityInArea = world.getEntitiesOfClass(ZombieEntity.class, new AxisAlignedBB(player.getX() + -4, player.getY() + -4, player.getZ() + -4, player.getX() + 4, player.getY() + 4, player.getZ() + 4)).isEmpty();

            if (!entityInArea) {
                if (stack.getItem() == ModItems.MASTER_SWORD.get()) {
                    int normalSwordSlot = player.inventory.findSlotMatchingItem(new ItemStack(ModItems.MASTER_SWORD.get()));
                    player.inventory.getSelected().shrink(1);

                    player.inventory.add(normalSwordSlot, new ItemStack(ModItems.MASTER_SWORD_AWAKENED.get()));
                }
            } else if (stack.getItem() == ModItems.MASTER_SWORD_AWAKENED.get()) {
                int awakenedSwordSlot = player.inventory.findSlotMatchingItem(new ItemStack(ModItems.MASTER_SWORD_AWAKENED.get()));
                player.inventory.getSelected().shrink(1);

                player.inventory.add(awakenedSwordSlot, new ItemStack(ModItems.MASTER_SWORD.get()));
            }
            ticksPast = 0;
        } else ticksPast++;
    }

    // Frozen Effect Events

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickEmpty event) {
        if (event.isCancelable() && event.getEntityLiving().hasEffect(ModEffects.FROZEN_EFFECT.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {
        if (event.isCancelable() && event.getEntityLiving().hasEffect(ModEffects.FROZEN_EFFECT.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCancelable() && event.getEntityLiving().hasEffect(ModEffects.FROZEN_EFFECT.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.isCancelable() && event.getEntityLiving().hasEffect(ModEffects.FROZEN_EFFECT.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        PlayerEntity player = event.getPlayer();
        if (event.isCancelable() && player.hasEffect(ModEffects.FROZEN_EFFECT.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        PlayerEntity player = event.getPlayer();
        if (event.isCancelable() && player.hasEffect(ModEffects.FROZEN_EFFECT.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingHurtEvent event) {
        if (event.getSource().isFire() && event.getEntityLiving().hasEffect(ModEffects.FROZEN_EFFECT.get())) {
            event.getEntityLiving().removeEffectNoUpdate(ModEffects.FROZEN_EFFECT.get());
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (entity.hasEffect(ModEffects.FROZEN_EFFECT.get()) && entity.isOnGround()) {
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        if (event.isCancelable() && event.getEntityLiving().hasEffect(ModEffects.FROZEN_EFFECT.get())) {
            event.setCanceled(true);
        }
    }

    // Block Events

    // Item Events

    @SubscribeEvent
    public void onPlayerCloneDeath(PlayerEvent.Clone event) {
        ModifiableAttributeInstance original = event.getOriginal().getAttribute(Attributes.MAX_HEALTH);
        if (original != null) {
            AttributeModifier healthModifier = original.getModifier(HeartContainerItem.healthModifierUuid);
            if (healthModifier != null) {
                event.getPlayer().getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthModifier);
            }
        }
    }
}