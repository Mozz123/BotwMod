package botwmod.registry;

import botwmod.BotwMod;
import botwmod.world.structures.CartsStructure;
import botwmod.world.structures.MasterSwordPedestalStructure;
import botwmod.world.structures.RuinsStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, BotwMod.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> MASTER_SWORD_PEDESTAL = STRUCTURES.register("master_sword_pedestal", () -> (new MasterSwordPedestalStructure(NoFeatureConfig.CODEC)));
    public static final RegistryObject<Structure<NoFeatureConfig>> CARTS_STRUCTURE = STRUCTURES.register("carts_structure", () -> (new CartsStructure(NoFeatureConfig.CODEC)));
    public static final RegistryObject<Structure<NoFeatureConfig>> RUINS_STRUCTURE = STRUCTURES.register("ruins_structure", () -> (new RuinsStructure(NoFeatureConfig.CODEC)));

    public static void setupStructures() {
        setupMapSpacingAndLand(
                MASTER_SWORD_PEDESTAL.get(),
                new StructureSeparationSettings(50, 40, 10685344), true);
        setupMapSpacingAndLand(
                CARTS_STRUCTURE.get(),
                new StructureSeparationSettings(30, 20, 15784567), true);
        setupMapSpacingAndLand(
                RUINS_STRUCTURE.get(),
                new StructureSeparationSettings(30, 20, 15784567), true);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand){
            Structure.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        DimensionStructuresSettings.DEFAULTS =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.DEFAULTS)
                        .put(structure, structureSeparationSettings)
                        .build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
            if(structureMap instanceof ImmutableMap){
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }
}