package botwmod.registry;

import botwmod.BOTWItemGroup;
import botwmod.BotwMod;
import botwmod.items.BombItem;
import botwmod.items.HeartContainerItem;
import botwmod.items.MasterSwordItem;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
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

    // Key Items
    public static final RegistryEntry<HeartContainerItem> HEART_CONTAINER = REGISTRATE.item("heart_container", HeartContainerItem::new).defaultLang()
            .properties(p -> p.maxStackSize(16).rarity(Rarity.RARE)).register();

    // Swords
    public static final RegistryEntry<MasterSwordItem> MASTER_SWORD = REGISTRATE.item("master_sword", prop -> new MasterSwordItem(ModToolTiers.MASTER_SWORD, 7, -2.4f, prop))
            .model((ctx, provider) -> provider.handheld(ctx::getEntry)).register();
    public static final RegistryEntry<MasterSwordItem> MASTER_SWORD_AWAKENED = REGISTRATE.item("master_sword_awakened", prop -> new MasterSwordItem(ModToolTiers.MASTER_SWORD_AWAKENED, 14, -2.4f, prop))
            .model((ctx, provider) -> provider.handheld(ctx::getEntry)).register();

    // Projectiles
    public static final RegistryEntry<Item> BEAM_ATTACK = REGISTRATE.item("beam_attack", Item::new).defaultLang().properties(p -> p).register();
    public static final RegistryEntry<BombItem> BOMB = REGISTRATE.item("bomb", BombItem::new).defaultLang().properties(p -> p).register();

    public static void load() {
        LOGGER.info("Items registered");
    }
}