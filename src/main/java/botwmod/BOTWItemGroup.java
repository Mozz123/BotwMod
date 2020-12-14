package botwmod;

import botwmod.registry.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class BOTWItemGroup extends ItemGroup {
    public BOTWItemGroup(String label) {
        super(label);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.AMBER_ORE.get());
    }

}
