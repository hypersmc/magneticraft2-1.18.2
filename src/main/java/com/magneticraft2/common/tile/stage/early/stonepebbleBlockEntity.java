package com.magneticraft2.common.tile.stage.early;

import com.magneticraft2.common.block.stage.early.stonepebble;
import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

/**
 * @author JumpWatch on 26-03-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class stonepebbleBlockEntity extends BlockEntity {
    private static stonepebbleBlockEntity self;
    public stonepebbleBlockEntity(BlockPos pos, BlockState state) {
        super(FinalRegistry.stonepebbleBlockEntity.get(), pos, state);
        self = this;
    }
    public static <E extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState estate, E e) {
        if (!level.isClientSide()){
            int type = estate.getValue(stonepebble.TYPE);
            if (type == 0){
                Random random = new Random();
                int randomnum = random.nextInt(4) + 1;
                BlockState currentState = level.getBlockState(pos);
                BlockState newState = currentState.setValue(stonepebble.TYPE, randomnum);
                level.setBlock(pos, newState, 3);
            }
        }
    }
}
