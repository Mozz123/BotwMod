package botwmod.registry;

import botwmod.BotwMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModConfiguredStructures {
    public static StructureFeature<?, ?> CONFIGURED_MASTER_SWORD_PEDESTAL = ModStructures.MASTER_SWORD_PEDESTAL.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(BotwMod.MODID, "configured_master_sword_pedestal"), CONFIGURED_MASTER_SWORD_PEDESTAL);
        FlatGenerationSettings.STRUCTURES.put( ModStructures.MASTER_SWORD_PEDESTAL.get(), CONFIGURED_MASTER_SWORD_PEDESTAL);
    }
}