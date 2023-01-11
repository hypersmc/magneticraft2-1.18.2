package com.magneticraft2.common.tile;

import com.magneticraft2.common.block.BlockMultiBlockBase;
import com.magneticraft2.common.utils.MachinesUtils;
import com.magneticraft2.common.utils.generalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class BlockEntityMultiBlockBase <TE extends BlockEntityMultiBlockBase> extends TileEntityMagneticraft2 {
    boolean firstTick = false;
    private static BlockEntityMultiBlockBase BE;
    private boolean isMaster;
    private boolean breaking;
    private boolean startBreaking;
    private TE masterTE;
    private boolean masterChecked = false;
    private boolean faceChecked = false;
    private int faceIndex;

    public BlockEntityMultiBlockBase(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }


    public static <E extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, E e) {
        if (!BE.firstTick) {
            BE.firstTick = true;
            BE.isMaster();
            if (BE.isMaster()) BE.setMaster();
            BE.onFirstTick();
        }
    }


    public void onTick() {
    }

    public void onFirstTick() {
    }
    public TE getMaster()
    {
        if (isMaster) return (TE) this;
        if (masterTE == null || masterTE.isRemoved())
        {
            List<BlockPos> list = MachinesUtils.getBlocksIn3x3x3Centered(this.worldPosition);
            for (BlockPos currentPos : list)
            {
                BlockEntity te = level.getBlockEntity(currentPos);
                if (te instanceof BlockEntityMultiBlockBase
                        && ((BlockEntityMultiBlockBase) te).isMaster()
                        && instanceOf(te))
                {
                    setMaster((TE) te);
                    ((TE) te).setMaster();
                    return masterTE;
                }
            }
            if (!level.isClientSide && !startBreaking)
            {
                generalUtils.sendConsoleMessage("MultiBlock Machine: " + this.getBlockState() + " has no Master at " + worldPosition);
                generalUtils.sendConsoleMessage(" Break this machine and try replace it, If this does not work, report the problem:");
                generalUtils.sendConsoleMessage("Link to issue making is coming soon");
            }
            return (TE) this;
        }
        return masterTE;
    }
    public void setMaster()
    {
        if (!isMaster()) return;
        List<BlockPos> list = getListOfBlockPositions(worldPosition);
        for (BlockPos currentPos : list)
        {
            BlockEntity te = level.getBlockEntity(currentPos);
            if (te instanceof BlockEntityMultiBlockBase && instanceOf(te))
            {
                ((BlockEntityMultiBlockBase) te).setMaster(this);
            }
        }
    }
    public void breakMultiBlocks()
    {
        startBreaking = true;
        if (!this.isMaster())
        {
            if (getMaster() != this)
            {
                getMaster().breakMultiBlocks();
            }
            return;
        }
        if (!breaking)
        {
            breaking = true;
            onMasterBreak();
            List<BlockPos> list = getListOfBlockPositions(worldPosition);
            for (BlockPos currentPos : list)
            {
                Block block = level.getBlockState(currentPos).getBlock();
                if (block instanceof BlockMultiBlockBase) level.destroyBlock(currentPos, false);
            }
        }
    }

    public List<BlockPos> getListOfBlockPositions(BlockPos centerPosition)
    {
        return MachinesUtils.getBlocksIn3x3x3Centered(centerPosition);
    }
    public abstract boolean instanceOf(BlockEntity blockEntity);

    public Direction getMasterFacing()
    {
        if (faceChecked) return Direction.from3DDataValue(faceIndex);
        Direction facing = getBlockFace();
        faceChecked = true;
        faceIndex = facing.get3DDataValue();
        return facing;
    }
    public Direction getBlockFace()
    {
        BlockState state = level.getBlockState(getBlockPos());
        if (state.getBlock() instanceof BlockMultiBlockBase)
            return state.getValue(BlockMultiBlockBase.FACING2);
        return Direction.NORTH;
    }
    public void onMasterBreak()
    {
    }
    public boolean isMaster()
    {
        if (masterChecked) return this.isMaster;

        BlockState state = this.level.getBlockState(this.getBlockPos());
        if (!(state.getBlock() instanceof BlockMultiBlockBase)) return false;
        isMaster = state.getValue(BlockMultiBlockBase.MASTER);
        masterChecked = true;
        return isMaster;
    }
    public void setMaster(TE master)
    {
        this.masterTE = master;
    }

    @Override
    public void load(CompoundTag tag) {
        this.isMaster = tag.getBoolean("master");
        this.masterChecked = tag.getBoolean("checked");
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putBoolean("master", this.isMaster);
        tag.putBoolean("checked", this.masterChecked);
        super.saveAdditional(tag);
    }
}
