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
    public static final UUID healthModifierUuid = UUID.fromString("89597ef8-44bf-11eb-b378-0242ac130002");

    public HeartContainerItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.getCooldowns().isOnCooldown(this)) {
            return super.use(world, player, hand);
        }
        ModifiableAttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute.getValue() < MAX) {

            AttributeModifier oldHealthModifier = healthAttribute.getModifier(healthModifierUuid);
            double addedHealth = (oldHealthModifier == null) ? +4.0D : oldHealthModifier.getAmount() + 4.0D;
            healthAttribute.removeModifier(healthModifierUuid);
            AttributeModifier healthModifier = new AttributeModifier(healthModifierUuid, "Heart Container HP Bonus", addedHealth, AttributeModifier.Operation.ADDITION);
            healthAttribute.addPermanentModifier(healthModifier);
            player.getCooldowns().addCooldown(this, COOLDOWN);
            player.getItemInHand(hand).shrink(1);
        }
        return super.use(world, player, hand);
    }
}