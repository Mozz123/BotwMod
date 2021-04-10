package botwmod;

import botwmod.registry.*;
import botwmod.setup.*;
import botwmod.world.OreGeneration;
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
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
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

    public static final BOTWItemGroup ITEM_GROUP = new BOTWItemGroup(BotwMod.MODID);
    public static Registrate REGISTRATE;

    public BotwMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(ClientEventHandler::init);
        modEventBus.addListener(CommonEventHandler::init);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::onBiomeLoadingEvent);

        REGISTRATE = Registrate.create(BotwMod.MODID);

        ModBlocks.load();
        ModItems.load();
        ModEntities.load();
        ModTiles.load();
        ModProfessions.PROFESSIONS.register(modEventBus);
        ModProfessions.POI_TYPES.register(modEventBus);
        ModEffects.EFFECTS.register(modEventBus);
        ModStructures.STRUCTURES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ItemEvents());

        event.enqueueWork(() -> {
            ModStructures.setupStructures();
            ModConfiguredStructures.registerConfiguredStructures();
        });
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.TAIGA || event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.PLAINS
                || event.getCategory() == Biome.Category.JUNGLE || event.getCategory() == Biome.Category.SWAMP) {
            event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_MASTER_SWORD_PEDESTAL);
        }
    }

    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.getDimensionType().equals(World.OVERWORLD)) {
                return;
            }
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().getChunkGenerator().func_235957_b_().field_236193_d_);
            tempMap.putIfAbsent(ModStructures.MASTER_SWORD_PEDESTAL.get(), DimensionStructuresSettings.field_236191_b_.get(ModStructures.MASTER_SWORD_PEDESTAL.get()));
            serverWorld.getChunkProvider().getChunkGenerator().func_235957_b_().field_236193_d_ = tempMap;
        }
    }

    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(BotwMod.MODID, path);
    }
}