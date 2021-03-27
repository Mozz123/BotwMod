package botwmod.items.arrows;

import botwmod.entity.projectile.arrows.FireArrowEntity;
import botwmod.entity.projectile.arrows.IceArrowEntity;
import botwmod.registry.ModEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FireArrowItem extends ArrowItem {
    public FireArrowItem(Item.Properties builder) {
        super(builder);
    }

    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new FireArrowEntity(ModEntities.FIRE_ARROW.get(), shooter, worldIn);
    }
}