package botwmod;

import botwmod.registry.ModBlocks;
import botwmod.registry.ModItems;
import botwmod.setup.CommonEventHandler;
import botwmod.world.OreGeneration;
import com.tterrag.registrate.Registrate;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

@Mod(BotwMod.MODID)
public class BotwMod {

    public static final String MODID = "botwmod";
    public static final String MOD_NAME = "BOTWMod";
    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    public static final BOTWItemGroup ITEM_GROUP = new BOTWItemGroup(BotwMod.MODID);
    public static Registrate REGISTRATE;

    public BotwMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonEventHandler::init);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::onBiomeLoadingEvent);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE = Registrate.create(BotwMod.MODID);
        REGISTRATE.itemGroup(() -> ITEM_GROUP);

        ModBlocks.load();
        ModItems.load();

        MinecraftForge.EVENT_BUS.register(this);
    }
}