package botwmod.registry;

import botwmod.BotwMod;
import botwmod.blocks.tile.MasterSwordPedestalTile;
import com.tterrag.registrate.util.entry.TileEntityEntry;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModTiles {
    private static final Logger LOGGER = LogManager.getLogger(BotwMod.MODID + "-Blocks");

    public static final TileEntityEntry<MasterSwordPedestalTile> MASTER_SWORD_PEDESTAL = TileEntityEntry.cast(ModBlocks.MASTER_SWORD_PEDESTAL.getSibling(ForgeRegistries.TILE_ENTITIES));

    public static void load() {
        LOGGER.info("Blocks registered");
    }
}