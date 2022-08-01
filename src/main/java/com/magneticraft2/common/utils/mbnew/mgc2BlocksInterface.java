package com.magneticraft2.common.utils.mbnew;

import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class mgc2BlocksInterface {
    public interface BlockstateProvider
    {
        BlockState getState();

        void setState(BlockState newState);
    }


    public interface IGeneralMultiblock extends BlockstateProvider
    {
        @Nullable
        IGeneralMultiblock master();

        default boolean isDummy()
        {
            BlockState state = getState();
            if(state.hasProperty(mgc2Properties.MULTIBLOCKSLAVE))
                return state.getValue(mgc2Properties.MULTIBLOCKSLAVE);
            else
                return true;
        }
    }
}
