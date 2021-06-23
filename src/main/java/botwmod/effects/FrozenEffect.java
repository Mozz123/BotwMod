package botwmod.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class FrozenEffect extends Effect {

    public FrozenEffect(EffectType type, int liquidColor) {
        super(type, liquidColor);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}