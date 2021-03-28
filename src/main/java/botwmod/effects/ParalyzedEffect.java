package botwmod.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ParalyzedEffect extends Effect {

    public ParalyzedEffect(EffectType type, int liquidColor) {
        super(type, liquidColor);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}