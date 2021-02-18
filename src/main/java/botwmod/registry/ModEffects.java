package botwmod.registry;

import botwmod.BotwMod;
import botwmod.effects.FrozenEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, BotwMod.MODID);

    public static final RegistryObject<Effect> FROZEN_EFFECT = EFFECTS.register("frozen", FrozenEffect::new);
}