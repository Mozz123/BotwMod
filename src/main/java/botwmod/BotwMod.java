package botwmod;

import botwmod.registry.*;
import botwmod.setup.BotwItemGroup;
import botwmod.setup.ClientEventHandler;
import botwmod.setup.CommonEventHandler;
import botwmod.world.OreGeneration;
import com.mojang.serialization.Codec;
import com.tterrag.registrate.Registrate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Mod(BotwMod.MODID)
public class BotwMod {

    public static final String MODID = "botwmod";
    public static final String MOD_NAME = "BOTWMod";
    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    public static final BotwItemGroup ITEM_GROUP = new BotwItemGroup(BotwMod.MODID);
    public static Registrate REGISTRATE;

    public BotwMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ClientEventHandler::init);
        modEventBus.addListener(CommonEventHandler::init);
        modEventBus.addListener(this::setup);
        ModProfessions.PROFESSIONS.register(modEventBus);
        ModProfessions.POI_TYPES.register(modEventBus);
        ModEffects.EFFECTS.register(modEventBus);
        ModStructures.STRUCTURES.register(modEventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.HIGH, OreGeneration::onBiomeLoadingEvent);
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);

        REGISTRATE = Registrate.create(BotwMod.MODID);

        ModBlocks.load();
        ModItems.load();
        ModEntities.load();
        ModTiles.load();

        forgeBus.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModStructures.setupStructures();
            ModConfiguredStructures.registerConfiguredStructures();
        });
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.FOREST) {
            event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_MASTER_SWORD_PEDESTAL);
        }
        event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_CARTS_STRUCTURE);
    }

    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                BotwMod.logger.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(ModStructures.MASTER_SWORD_PEDESTAL.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.MASTER_SWORD_PEDESTAL.get()));
            tempMap.putIfAbsent(ModStructures.CARTS_STRUCTURE.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.CARTS_STRUCTURE.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }

    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(BotwMod.MODID, path);
    }
}