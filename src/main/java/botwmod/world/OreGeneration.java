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

    public static ConfiguredFeature<?, ?> ORE_AMBER = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.AMBER_ORE.get().getDefaultState(), 4))
            .range(16).square();
    public static ConfiguredFeature<?, ?> ORE_OPAL = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.OPAL_ORE.get().getDefaultState(), 4))
            .range(16).square();
    public static ConfiguredFeature<?, ?> ORE_RUBY = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.RUBY_ORE.get().getDefaultState(), 3))
            .range(16).square();
    public static ConfiguredFeature<?, ?> ORE_SAPPHIRE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.SAPPHIRE_ORE.get().getDefaultState(), 2))
            .range(16).square();
    public static ConfiguredFeature<?, ?> ORE_TOPAZ = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.TOPAZ_ORE.get().getDefaultState(), 3))
            .range(16).square();

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.NETHER) {

        } else if (event.getCategory() == Biome.Category.THEEND) {

        } else {
            event.getGeneration().withFeature(undergroundOre, ORE_AMBER).withFeature(undergroundOre, ORE_OPAL).withFeature(undergroundOre, ORE_RUBY).withFeature(undergroundOre, ORE_SAPPHIRE).withFeature(undergroundOre, ORE_TOPAZ);
        }
    }
}