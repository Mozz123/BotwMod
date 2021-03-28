package botwmod.registry;

import botwmod.BotwMod;
import botwmod.effects.FrozenEffect;
import botwmod.effects.ParalyzedEffect;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class ModEffects {
    protected static final UUID FROZEN_UUID = UUID.fromString("02de0c75-cfc1-4313-8f2d-8a3a91b24756");
    protected static final UUID PARALYZED_UUID = UUID.fromString("85259943-28d6-493f-bb64-4686a5341ed5");

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, BotwMod.MODID);

    public static final RegistryObject<Effect> FROZEN_EFFECT = EFFECTS.register("frozen", () -> new FrozenEffect(EffectType.HARMFUL, 0xd8e7ff).addAttributesModifier(Attributes.MOVEMENT_SPEED, FROZEN_UUID.toString(), -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<Effect> PARALYZED_EFFECT = EFFECTS.register("paralyzed", () -> new ParalyzedEffect(EffectType.HARMFUL, 0xd8e7ff).addAttributesModifier(Attributes.MOVEMENT_SPEED, PARALYZED_UUID.toString(), -1000.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));

}