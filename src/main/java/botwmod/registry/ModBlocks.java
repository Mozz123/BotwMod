package botwmod.registry;

import botwmod.BotwMod;
import botwmod.blocks.FrozenSpikesBlock;
import botwmod.blocks.SwordPedestalBlock;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static botwmod.BotwMod.REGISTRATE;

public class ModBlocks {

    private static final Logger LOGGER = LogManager.getLogger(BotwMod.MODID + "-Blocks");

    // Ores

    public static final RegistryEntry<Block> SAPPHIRE_ORE = registerGemstoneOre("sapphire_ore", "Sapphire Ore", ModItems.SAPPHIRE);

    // Normal Blocks

    public static final RegistryEntry<FrozenSpikesBlock> FROZEN_SPIKES = REGISTRATE
            .object("frozen_spikes")
            .block(FrozenSpikesBlock::new)
            .properties(prop -> prop.noCollission().instabreak().noOcclusion())
            .addLayer(() -> RenderType::cutoutMipped)
            .blockstate((ctx, provider) -> provider.simpleBlock(ctx.getEntry(), provider.models().cross(ctx.getName(), BotwMod.getLocation("block/" +ctx.getName()))))
            .defaultLang()
            .register();

    public static final RegistryEntry<SwordPedestalBlock> SWORD_PEDESTAL = REGISTRATE
            .object("sword_pedestal")
            .block(SwordPedestalBlock::new)
            .properties(prop -> prop.harvestTool(ToolType.PICKAXE).strength(3.0F, 3.0F))
            .simpleItem()
            .defaultBlockstate()
            .defaultLang()
            .register();

    public static final RegistryEntry<Block> JEWELING_TABLE = REGISTRATE
            .object("jeweling_table")
            .block(Block::new)
            .initialProperties(Material.WOOD, Material.WOOD.getColor())
            .properties(prop -> prop.strength(2.0F).harvestTool(ToolType.AXE).sound(SoundType.WOOD))
            .simpleItem()
            .defaultBlockstate()
            .defaultLang()
            .register();

    // Presets

    public static final RegistryEntry<Block> registerGemstoneOre(String id, String lang, NonNullSupplier<Item> gemstone) {
        return REGISTRATE.object(id).block(Block::new).initialProperties(Material.STONE, Material.STONE.getColor()).properties(p -> p.strength(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)).simpleItem().lang(lang).defaultBlockstate()
                .loot((tables, block) -> tables.add(block, LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(new RandomValueRange(1))
                                .add(AlternativesLootEntry.alternatives(ItemLootEntry.lootTableItem(block)
                                        .when(Alternative.alternative(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))))))
                                .add(ItemLootEntry.lootTableItem(gemstone.get())
                                        .apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))))).simpleItem().register();
    }

    public static void load() {
        LOGGER.info("Blocks registered");
    }
}
