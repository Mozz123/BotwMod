package botwmod.items;

import botwmod.registry.ModItems;
import botwmod.setup.CommonEventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MasterSwordItem extends SwordItem {
    public MasterSwordItem(IItemTier tier, int attackDamage, float attackSpeed, Item.Properties builder) {
        super(tier, attackDamage, attackSpeed, builder);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        int durabilityLeft = stack.getMaxDamage() - stack.getDamageValue();

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (durabilityLeft <= 2 && stack.getItem() == ModItems.MASTER_SWORD.get()) {
                player.getCooldowns().addCooldown(this, 18000);
                stack.setDamageValue(0);
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        CommonEventHandler.onRightClick(playerIn, itemstack);
        return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
    }
}