package botwmod.registry;

import botwmod.BotwMod;
import botwmod.blocks.AmberOreBlock;
import botwmod.blocks.FrozenSpikesBlock;
import botwmod.blocks.MasterSwordPedestalBlock;
import botwmod.blocks.tile.MasterSwordPedestalTile;
import botwmod.client.render.tile.MasterSwordPedestalTESR;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ApplyBonus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static botwmod.BotwMod.REGISTRATE;

public class ModBlocks {

    private static final Logger LOGGER = LogManager.getLogger(BotwMod.MODID + "-Blocks");

    // Ores
    public static final RegistryEntry<AmberOreBlock> SAPPHIRE_ORE = registerGemstoneOre("sapphire_ore", "Sapphire Ore", ModItems.SAPPHIRE);

    // Normal Blocks
    public static final RegistryEntry<FrozenSpikesBlock> FROZEN_SPIKES = REGISTRATE.object("frozen_spikes").block(FrozenSpikesBlock::new).properties(prop -> prop.doesNotBlockMovement().zeroHardnessAndResistance().notSolid()).addLayer(() -> RenderType::getCutoutMipped).blockstate((ctx, provider) -> provider.simpleBlock(ctx.getEntry(), provider.models().cross(ctx.getName(), BotwMod.getLocation("block/" +ctx.getName())))).defaultLang().register();
    public static final RegistryEntry<MasterSwordPedestalBlock> MASTER_SWORD_PEDESTAL = REGISTRATE.object("master_sword_pedestal").block(MasterSwordPedestalBlock::new).properties(prop -> prop.hardnessAndResistance(-1.0F, 3600000.0F).noDrops()).tileEntity(MasterSwordPedestalTile::new).renderer(() -> MasterSwordPedestalTESR::new).build().defaultBlockstate().defaultLang().register();
    public static final RegistryEntry<MasterSwordPedestalBlock> SWORD_PEDESTAL = REGISTRATE.object("sword_pedestal").block(MasterSwordPedestalBlock::new).properties(prop -> prop.setRequiresTool().hardnessAndResistance(1.5F, 6.0F)).tileEntity(MasterSwordPedestalTile::new).renderer(() -> MasterSwordPedestalTESR::new).build().defaultBlockstate().defaultLang().simpleItem().register();

    // Presets
    public static final RegistryEntry<AmberOreBlock> registerGemstoneOre(String id, String lang, NonNullSupplier<Item> gemstone) {
        return REGISTRATE.object(id).block(AmberOreBlock::new).initialProperties(Material.ROCK, Material.ROCK.getColor()).properties(p -> p.hardnessAndResistance(3.0f, 3.0f)).lang(lang).defaultBlockstate()
                .loot((tables, block) -> tables.registerLootTable(block, LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(new RandomValueRange(1))
                                .addEntry(AlternativesLootEntry.builder(ItemLootEntry.builder(block)
                                        .acceptCondition(Alternative.builder(MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))))))
                                .addEntry(ItemLootEntry.builder(gemstone.get())
                                        .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))))).simpleItem().register();
    }

    public static void load() {
        LOGGER.info("Blocks registered");
    }
}
