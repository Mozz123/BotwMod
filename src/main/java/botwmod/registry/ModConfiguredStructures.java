package botwmod.registry;

import botwmod.BotwMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModConfiguredStructures {
    public static StructureFeature<?, ?> CONFIGURED_MASTER_SWORD_PEDESTAL = ModStructures.MASTER_SWORD_PEDESTAL.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_CARTS_STRUCTURE = ModStructures.CARTS_STRUCTURE.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_RUINS_STRUCTURE = ModStructures.RUINS_STRUCTURE.get().configured(IFeatureConfig.NONE);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry, new ResourceLocation(BotwMod.MODID, "configured_master_sword_pedestal"), CONFIGURED_MASTER_SWORD_PEDESTAL);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.MASTER_SWORD_PEDESTAL.get(), CONFIGURED_MASTER_SWORD_PEDESTAL);

        Registry.register(registry, new ResourceLocation(BotwMod.MODID, "configured_carts_structure"), CONFIGURED_CARTS_STRUCTURE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.CARTS_STRUCTURE.get(), CONFIGURED_CARTS_STRUCTURE);

        Registry.register(registry, new ResourceLocation(BotwMod.MODID, "configured_ruins_structure"), CONFIGURED_RUINS_STRUCTURE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.RUINS_STRUCTURE.get(), CONFIGURED_RUINS_STRUCTURE);
    }
}