package botwmod.setup;

import botwmod.registry.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class BOTWItemGroup extends ItemGroup {
    public BOTWItemGroup(String label) {
        super(label);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.MASTER_SWORD.get());
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        registerWeapons(items);
        registerValuables(items);
        registerKeyItems(items);
        registerProjectiles(items);
    }

    private void registerWeapons(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.MASTER_SWORD.get()));
    }

    private void registerValuables(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.AMBER.get()));
        items.add(new ItemStack(ModItems.OPAL.get()));
        items.add(new ItemStack(ModItems.RUBY.get()));
        items.add(new ItemStack(ModItems.SAPPHIRE.get()));
        items.add(new ItemStack(ModItems.TOPAZ.get()));
    }

    private void registerKeyItems(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.HEART_CONTAINER.get()));
    }

    private void registerProjectiles(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.BOMB.get()));
        items.add(new ItemStack(ModItems.BOMB_ARROW.get()));
        items.add(new ItemStack(ModItems.ICE_ARROW.get()));
    }
}