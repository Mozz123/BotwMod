package botwmod.registry;

import botwmod.BotwMod;
import botwmod.structures.MasterSwordPedestalStructure;
import botwmod.structures.pieces.MasterSwordPedestalStructurePieces;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, BotwMod.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> MASTER_SWORD_PEDESTAL = STRUCTURES.register("run_down_house", () -> (new MasterSwordPedestalStructure(NoFeatureConfig.CODEC)));
    public static IStructurePieceType MASTER_SWORD_PEDESTAL_PIECE = MasterSwordPedestalStructurePieces.Piece::new;

    public static void setupStructures() {
        setupMapSpacingAndLand(MASTER_SWORD_PEDESTAL.get(), new StructureSeparationSettings(10, 5, 267472), true);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand) {
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }

        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }
}