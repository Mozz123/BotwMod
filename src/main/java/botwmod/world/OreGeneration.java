package botwmod.world;

import botwmod.registry.ModBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {

    public static Decoration undergroundOre = GenerationStage.Decoration.UNDERGROUND_ORES;

    public static ConfiguredFeature<?, ?> ORE_SAPPHIRE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.SAPPHIRE_ORE.get().defaultBlockState(), 2))
            .range(16).squared();

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.NETHER) {

        } else if (event.getCategory() == Biome.Category.THEEND) {

        } else {
            event.getGeneration().addFeature(undergroundOre, ORE_SAPPHIRE);
        }
    }
}