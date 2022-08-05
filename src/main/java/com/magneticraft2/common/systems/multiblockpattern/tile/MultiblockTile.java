package com.magneticraft2.common.systems.multiblockpattern.tile;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;


public class MultiblockTile extends BlockEntity implements IMultiblock{

    private BlockPos masterPos = BlockPos.ZERO;

    public MultiblockTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public MultiblockTile(BlockPos pos, BlockState state) {
        super(FinalRegistry.MUTIBLOCK.get(), pos, state);
    }

    @Override
    public BlockPos getMaster() {
        return this.masterPos;
    }

    @Override
    public BlockState getMasterState() {
        return level.getBlockState(masterPos);
    }

    @Override
    public MultiblockMasterTile getMasterTile() {
        return (MultiblockMasterTile)level.getBlockEntity(masterPos);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.masterPos = BlockPos.of(compound.getLong("master_pos"));
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.putLong("master_pos", this.masterPos.asLong());
    }

    @Override
    public void setMasterPos(BlockPos pos) {
        this.masterPos = pos;
        this.setChanged();
    }


}
