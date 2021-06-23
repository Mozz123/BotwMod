package botwmod.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrozenSpikesBlock extends Block {
    public FrozenSpikesBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            entityIn.hurt(DamageSource.SWEET_BERRY_BUSH, 1);
            EffectInstance effectinstance = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 1);
            ((LivingEntity) entityIn).addEffect(effectinstance);
        }
    }
}