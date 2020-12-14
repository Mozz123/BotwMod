package botwmod.registry;

import botwmod.BotwMod;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static botwmod.BotwMod.REGISTRATE;

public class ModItems {

    private static final Logger LOGGER = LogManager.getLogger(BotwMod.MODID + "-Items");

    // Gemstones
    public static final RegistryEntry<Item> AMBER = REGISTRATE.item("amber", Item::new).defaultLang().properties(p -> p).register();
    public static final RegistryEntry<Item> OPAL = REGISTRATE.item("opal", Item::new).defaultLang().properties(p -> p).register();
    public static final RegistryEntry<Item> RUBY = REGISTRATE.item("ruby", Item::new).defaultLang().properties(p -> p).register();
    public static final RegistryEntry<Item> SAPPHIRE = REGISTRATE.item("sapphire", Item::new).defaultLang().properties(p -> p).register();
    public static final RegistryEntry<Item> TOPAZ = REGISTRATE.item("topaz", Item::new).defaultLang().properties(p -> p).register();

    public static void load() {
        LOGGER.info("Items registered");
    }
}
