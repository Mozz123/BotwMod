package botwmod.setup;

import botwmod.items.HeartContainerItem;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemEvents {
    @SubscribeEvent
    public void onPlayerCloneDeath(PlayerEvent.Clone event) {
        ModifiableAttributeInstance original = event.getOriginal().getAttribute(Attributes.MAX_HEALTH);
        if (original != null) {
            AttributeModifier healthModifier = original.getModifier(HeartContainerItem.healthModifierUuid);
            if (healthModifier != null) {
                event.getPlayer().getAttribute(Attributes.MAX_HEALTH).applyPersistentModifier(healthModifier);
            }
        }
    }
}