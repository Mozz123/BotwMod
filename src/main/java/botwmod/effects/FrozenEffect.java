package botwmod.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class FrozenEffect extends Effect {
    public FrozenEffect() {
        super(EffectType.HARMFUL, 0xd8e7ff);
    }

    @Override
    public boolean isReady(int id, int amplifier) {
        return true;
    }
}