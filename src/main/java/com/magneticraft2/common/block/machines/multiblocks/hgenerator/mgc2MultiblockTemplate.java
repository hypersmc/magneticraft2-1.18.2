package com.magneticraft2.common.block.machines.multiblocks.hgenerator;

import com.magneticraft2.common.systems.mbsysnew.BlockMatcher;
import com.magneticraft2.common.systems.mbsysnew.ClientMultiblocks;
import com.magneticraft2.common.systems.mbsysnew.TemplateMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.function.Consumer;

public class mgc2MultiblockTemplate extends TemplateMultiblock {
    public mgc2MultiblockTemplate(ResourceLocation loc, BlockPos masterFromOrigin, BlockPos triggerFromOrigin, BlockPos size, List<BlockMatcher.MatcherPredicate> additionalPredicates) {
        super(loc, masterFromOrigin, triggerFromOrigin, size, additionalPredicates);
    }

    @Override
    public float getManualScale() {
        return 0;
    }

    @Override
    public void initializeClient(Consumer<ClientMultiblocks.MultiblockManualData> consumer) {

    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    protected void replaceStructureBlock(StructureTemplate.StructureBlockInfo info, Level world, BlockPos actualPos, boolean mirrored, Direction clickDirection, Vec3i offsetFromMaster) {
//        BlockState state = baseState.get().defaultBlockState();
//        if(!offsetFromMaster.equals(Vec3i.ZERO))
//            state = state.setValue(IEProperties.MULTIBLOCKSLAVE, true);
//        world.setBlockAndUpdate(actualPos, state);
//        BlockEntity curr = world.getBlockEntity(actualPos);
//        if(curr instanceof MultiblockPartBlockEntity<?> tile)
//        {
//            tile.formed = true;
//            tile.offsetToMaster = new BlockPos(offsetFromMaster);
//            tile.posInMultiblock = info.pos;
//            if(state.hasProperty(IEProperties.MIRRORED))
//                tile.setMirrored(mirrored);
//            tile.setFacing(transformDirection(clickDirection.getOpposite()));
//            tile.setChanged();
//            world.blockEvent(actualPos, world.getBlockState(actualPos).getBlock(), 255, 0);
//        }
    }
}
