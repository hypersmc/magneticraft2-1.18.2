package com.magneticraft2.common.block.machines.multiblocks.Solars;

import com.magneticraft2.common.block.BlockMagneticraft2;
import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class SolarPanelOutputBlock extends BlockMagneticraft2 implements EntityBlock {
    public SolarPanelOutputBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(assembled, false).setValue(iscore, false));
    }


    public BlockPattern TryFormMultiBlock(){
        if (pattern == null){
            pattern = BlockPatternBuilder.start()
                    .aisle("sss","sss","sgs","sss","sss")
                    .where('s', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.STONE)))
                    .where('b', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.STONE_BRICKS)))
                    .where('g', BlockInWorld.hasState(BlockStatePredicate.forBlock(FinalRegistry.Block_Heat_Generator.get())))
                    .build();
        }else {
            LOGGER.info("Multiblock not found!");
        }
        return pattern;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
}
