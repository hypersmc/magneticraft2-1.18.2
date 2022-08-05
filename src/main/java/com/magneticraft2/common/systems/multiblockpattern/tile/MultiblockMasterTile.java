package com.magneticraft2.common.systems.multiblockpattern.tile;
import java.util.ArrayList;
import java.util.List;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockMasterTile extends BlockEntity implements IMultiblock{

    private List<BlockPos> slavePoses = new ArrayList<>();

    public MultiblockMasterTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public MultiblockMasterTile(BlockPos pos, BlockState state) {
        super(FinalRegistry.MUTIBLOCK_MASTER.get(), pos, state);
    }

    @Override
    public BlockPos getMaster() {
        return this.getBlockPos();
    }

    @Override
    public BlockState getMasterState() {
        return this.getBlockState();
    }

    @Override
    public MultiblockMasterTile getMasterTile() {
        return this;
    }

    public void addSlavePos(BlockPos pos) {
        this.slavePoses.add(pos);
        this.setChanged();
    }

    public List<BlockPos> getSlavePositions(){
        return this.slavePoses;
    }

    @Override
    public void setMasterPos(BlockPos pos) {}

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        ListTag list = compound.getList("other_pos", Tag.TAG_COMPOUND);
        for(Tag base : list) {
            this.slavePoses.add(BlockPos.of(((LongTag)base).getAsLong()));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        ListTag list = new ListTag();
        for(BlockPos pos : this.slavePoses) {
            list.add(LongTag.valueOf(pos.asLong()));
        }
        compound.put("other_pos", list);
    }

}

