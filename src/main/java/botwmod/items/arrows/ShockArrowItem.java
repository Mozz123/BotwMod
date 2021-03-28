package botwmod.items.arrows;

import botwmod.entity.projectile.arrows.ShockArrowEntity;
import botwmod.registry.ModEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShockArrowItem extends ArrowItem {
    public ShockArrowItem(Properties builder) {
        super(builder);
    }

    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new ShockArrowEntity(ModEntities.SHOCK_ARROW.get(), shooter, worldIn);
    }
}