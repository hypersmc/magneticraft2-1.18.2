package com.magneticraft2.common.tile;

import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.multiblock.Multiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JumpWatch on 03-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class Multiblockfiller_tile extends BlockEntity {
    public static double x = -3000;
    public static double y = -3000;
    public static double z = -3000;
    public static BlockState blockState = null;
    private static Multiblockfiller_tile self;
    private Multiblock multiblock = null;
    private static final Logger LOGGER = LogManager.getLogger("MGC2Multiblock");


    public static void setMulitblockListener(Multiblock Multiblock) {
        if (getMultiblockListener() == null) {
            self.multiblock = Multiblock;
        }else if (Multiblock == null){
            self.multiblock = null;
        }

    }
    public static Multiblock getMultiblockListener() {
        LOGGER.info("Multiblock is " + self.multiblock);
        return self.multiblock;
    }
    public Multiblockfiller_tile(BlockPos pos, BlockState state) {
        super(FinalRegistry.Multiblockfiller_tile.get(), pos, state);
        self = this;
    }

    public static void setX(double x) {
        Multiblockfiller_tile.x = x;
    }
    public static void setBlockat(Level world){
        LOGGER.info("setting block at " + x + " " + y + " " + z + " with " + GetBlockState());
        world.setBlockAndUpdate(new BlockPos(x, y, z), blockState);
        blockState = null;
        x = -3000;
        y = -3000;
        z = -3000;
    }
    public static void setY(double y) {
        Multiblockfiller_tile.y = y;
    }
    public static void setZ(double z) {
        Multiblockfiller_tile.z = z;
    }
    public static BlockState GetBlockState() {
        return blockState;
    }
    public static BlockPos GetBlockPos() {
        return new BlockPos(self.multiblock.getBlockPos());
    }
    public static void SetBlockState(BlockState blockState) {
        Multiblockfiller_tile.blockState = blockState;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        if (blockState == null || x == -3000 || y == -3000 || z == -3000) {
            return;
        }

        tag.putDouble("x", x);
        tag.putDouble("y", y);
        tag.putDouble("z", z);
        tag.put("blockState", NbtUtils.writeBlockState(blockState));
        if (multiblock != null) {
            tag.putString("multiblock", multiblock.toString());
        }

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        x = tag.getDouble("x");
        y = tag.getDouble("y");
        z = tag.getDouble("z");
        blockState = NbtUtils.readBlockState(tag.getCompound("blockState"));
        super.load(tag);
    }
}
