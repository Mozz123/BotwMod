package botwmod.items.arrows;

import botwmod.entity.projectile.arrows.BombArrowEntity;
import botwmod.entity.projectile.arrows.IceArrowEntity;
import botwmod.registry.ModEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IceArrowItem extends ArrowItem {
    public IceArrowItem(Properties builder) {
        super(builder);
    }

    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new IceArrowEntity(ModEntities.ICE_ARROW.get(), shooter, worldIn);
    }
}