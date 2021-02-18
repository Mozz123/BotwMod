package botwmod.registry;

import botwmod.BotwMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModProfessions {

    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, BotwMod.MODID);
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, BotwMod.MODID);

    public static final RegistryObject<PointOfInterestType> JEWELER_POI = POI_TYPES.register("jeweler",
            () -> new PointOfInterestType("jeweler", PointOfInterestType.getAllStates(ModBlocks.TOPAZ_ORE.get()), 1, 1)
    );


    @SuppressWarnings("UnstableApiUsage")
    public static final RegistryObject<VillagerProfession> JEWELER = PROFESSIONS.register("jeweler",
            () -> new VillagerProfession("jeweler", JEWELER_POI.get(),
                    ImmutableSet.of(ItemStack.EMPTY.getItem()), ImmutableSet.of(Blocks.AIR), (SoundEvent) null)
    );

}