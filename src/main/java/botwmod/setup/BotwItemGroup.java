package botwmod.setup;

import botwmod.registry.ModBlocks;
import botwmod.registry.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class BotwItemGroup extends ItemGroup {
    public BotwItemGroup(String label) {
        super(label);
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.MASTER_SWORD.get());
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        registerWeapons(items);
        registerValuables(items);
        registerKeyItems(items);
        registerProjectiles(items);
        registerBlocks(items);
    }

    private void registerWeapons(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.MASTER_SWORD.get()));
    }

    private void registerValuables(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.SAPPHIRE.get()));
    }

    private void registerKeyItems(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.HEART_CONTAINER.get()));
    }

    private void registerProjectiles(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.BOMB.get()));
        items.add(new ItemStack(ModItems.BOMB_ARROW.get()));
        items.add(new ItemStack(ModItems.ICE_ARROW.get()));
        items.add(new ItemStack(ModItems.FIRE_ARROW.get()));
        items.add(new ItemStack(ModItems.SHOCK_ARROW.get()));
    }

    private void registerBlocks(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModBlocks.SWORD_PEDESTAL.get().asItem()));
        items.add(new ItemStack(ModBlocks.SAPPHIRE_ORE.get().asItem()));
    }
}