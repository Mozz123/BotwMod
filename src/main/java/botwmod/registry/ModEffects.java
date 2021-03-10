package botwmod.registry;

import botwmod.BotwMod;
import botwmod.effects.FrozenEffect;
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

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, BotwMod.MODID);

    public static final RegistryObject<Effect> FROZEN_EFFECT = EFFECTS.register("frozen", () -> new FrozenEffect(EffectType.HARMFUL, 0xd8e7ff).addAttributesModifier(Attributes.MOVEMENT_SPEED, FROZEN_UUID.toString(), -1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
}