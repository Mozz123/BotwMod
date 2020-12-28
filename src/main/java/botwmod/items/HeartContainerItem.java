package botwmod.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.UUID;

public class HeartContainerItem extends Item {
    private static final int MAX = 60;
    static final int COOLDOWN = 10;
    private static final UUID healthModifierUuid = UUID.fromString("89597ef8-44bf-11eb-b378-0242ac130002");

    public HeartContainerItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.getCooldownTracker().hasCooldown(this)) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        playerIn.getFoodStats().addStats(1, 4);
        ModifiableAttributeInstance healthAttribute = playerIn.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute.getValue() < MAX) {
            AttributeModifier oldHealthModifier = healthAttribute.getModifier(healthModifierUuid);
            double addedHealth = (oldHealthModifier == null) ? +4.0D : oldHealthModifier.getAmount() + 4.0D;
            healthAttribute.removeModifier(healthModifierUuid);
            AttributeModifier healthModifier = new AttributeModifier(healthModifierUuid, "Heart Container HP Bonus", addedHealth, AttributeModifier.Operation.ADDITION);
            healthAttribute.applyPersistentModifier(healthModifier);
            playerIn.getCooldownTracker().setCooldown(this, COOLDOWN);
            playerIn.getHeldItem(handIn).shrink(1);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}