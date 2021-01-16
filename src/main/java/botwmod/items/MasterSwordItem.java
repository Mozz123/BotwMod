package botwmod.items;

import botwmod.entity.projectile.MasterSwordBeamEntity;
import botwmod.registry.ModEntities;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class MasterSwordItem extends SwordItem {
    public MasterSwordItem(IItemTier tier, int attackDamage, float attackSpeed, Item.Properties builder) {
        super(tier, attackDamage, attackSpeed, builder);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        int durabilityLeft = stack.getMaxDamage() - stack.getDamage();

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (durabilityLeft <= 2) {
                player.getCooldownTracker().setCooldown(this, 18000);
                stack.setDamage(0);
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        CommonEventHandler.onLeftClick(playerIn, itemstack);
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }
}