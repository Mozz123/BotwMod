package botwmod.registry;

import botwmod.BotwMod;
import botwmod.blocks.tile.SwordPedestalTile;
import botwmod.client.render.tile.SwordPedestalTESR;
import com.tterrag.registrate.util.entry.TileEntityEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static botwmod.BotwMod.REGISTRATE;

public class ModTiles {
    private static final Logger LOGGER = LogManager.getLogger(BotwMod.MODID + "-Blocks");

    public static final TileEntityEntry<SwordPedestalTile> SWORD_PEDESTAL = REGISTRATE
            .tileEntity("sword_pedestal", SwordPedestalTile::new)
            .validBlock(ModBlocks.SWORD_PEDESTAL)
            .renderer(() -> SwordPedestalTESR::new)
            .register();

    public static void load() {
        LOGGER.info("Blocks registered");
    }
}