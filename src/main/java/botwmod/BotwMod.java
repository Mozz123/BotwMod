package botwmod;

import botwmod.registry.*;
import botwmod.setup.BOTWItemGroup;
import botwmod.setup.ClientEventHandler;
import botwmod.setup.CommonEventHandler;
import botwmod.world.OreGeneration;
import com.tterrag.registrate.Registrate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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

    public static final BOTWItemGroup ITEM_GROUP = new BOTWItemGroup(BotwMod.MODID);
    public static Registrate REGISTRATE;

    public BotwMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

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

        MinecraftForge.EVENT_BUS.register(this);
    }


    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(BotwMod.MODID, path);
    }
}