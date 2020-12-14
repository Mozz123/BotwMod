package botwmod.registry;

import botwmod.BotwMod;
import botwmod.blocks.AmberOreBlock;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IItemProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

import static botwmod.BotwMod.REGISTRATE;

public class ModBlocks {

    private static final Logger LOGGER = LogManager.getLogger(BotwMod.MODID + "-Blocks");

    // Ores
    public static final RegistryEntry<AmberOreBlock> AMBER_ORE = registerGemstoneOre("amber_ore", "Amber Ore", ModItems.AMBER);
    /*
    public static final RegistryEntry<AmberOreBlock> OPAL_ORE = REGISTRATE.object("opal_ore").block(AmberOreBlock::new).initialProperties(Material.ROCK, Material.ROCK.getColor()).properties(p -> p.hardnessAndResistance(3.0f, 3.0f))
            .defaultBlockstate().defaultLoot().simpleItem().register();
    public static final RegistryEntry<AmberOreBlock> RUBY_ORE = REGISTRATE.object("ruby_ore").block(AmberOreBlock::new).initialProperties(Material.ROCK, Material.ROCK.getColor()).properties(p -> p.hardnessAndResistance(3.0f, 3.0f))
            .defaultBlockstate().defaultLoot().simpleItem().register();
    public static final RegistryEntry<AmberOreBlock> SAPPHIRE_ORE = REGISTRATE.object("sapphire_ore").block(AmberOreBlock::new).initialProperties(Material.ROCK, Material.ROCK.getColor()).properties(p -> p.hardnessAndResistance(3.0f, 3.0f))
            .defaultBlockstate().defaultLoot().simpleItem().register();
    public static final RegistryEntry<AmberOreBlock> TOPAZ_ORE = REGISTRATE.object("topaz_ore").block(AmberOreBlock::new).initialProperties(Material.ROCK, Material.ROCK.getColor()).properties(p -> p.hardnessAndResistance(3.0f, 3.0f))
            .defaultBlockstate().defaultLoot().simpleItem().register();
     */

    // Presets
    public static final RegistryEntry<AmberOreBlock> registerGemstoneOre(String id, String lang, NonNullSupplier<Item> gemstone) {
        return REGISTRATE.object(id).block(AmberOreBlock::new).initialProperties(Material.ROCK, Material.ROCK.getColor()).properties(p -> p.hardnessAndResistance(3.0f, 3.0f)).lang(lang).defaultBlockstate()
                .loot((tables, block) -> tables.registerLootTable(block, LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(new RandomValueRange(1))
                                .addEntry(AlternativesLootEntry.builder(ItemLootEntry.builder(block)
                                        .acceptCondition(Alternative.builder(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))))))
                                .addEntry(ItemLootEntry.builder((StandaloneLootEntry.ILootEntryBuilder) gemstone)
                                        .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))))).simpleItem().register();
    }

    public static void load() {
        LOGGER.info("Blocks registered");
    }
}
