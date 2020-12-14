package botwmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class AmberOreBlock extends Block {

    public AmberOreBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 3;
    }
}
