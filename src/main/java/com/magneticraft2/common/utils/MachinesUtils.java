package com.magneticraft2.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class MachinesUtils
{
    public static List<BlockPos> getBlocksIn3x3x3Centered(BlockPos pos)
    {
        List<BlockPos> list = new ArrayList<>();
        for (int y = -1; y < 2; y++)
        {
            for (int z = -1; z < 2; z++)
            {
                for (int x = -1; x < 2; x++)
                {
                    list.add(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z));
                }
            }
        }
        return list;
    }

    public static List<BlockPos> getBlocksIn3x3x2Centered(BlockPos pos, Direction facing)
    {
        List<BlockPos> list = new ArrayList<>();
        boolean isSided = facing == Direction.EAST || facing == Direction.WEST;
        boolean invert = facing == Direction.NORTH || facing == Direction.WEST;
        for (int y = -1; y < 2; y++)
        {
            for (int z = 0; z < 2; z++)
            {
                for (int x = -1; x < 2; x++)
                {
                    int finalX = (isSided ? z : x);
                    int finalZ = (isSided ? x : z);
                    list.add(new BlockPos(pos.getX() + (invert ? -finalX : finalX), pos.getY() + y, pos.getZ() + (invert ? -finalZ : finalZ)));
                }
            }
        }
        return list;
    }

    public static List<BlockPos> getBlocksIn3x2x3CenteredPlus1OnTop(BlockPos pos)
    {
        List<BlockPos> list = new ArrayList<BlockPos>();
        for (int y = -1; y < 2; y++)
        {
            for (int z = -1; z < 2; z++)
            {
                for (int x = -1; x < 2; x++)
                {
                    if (y != 1 || (z == 0 && x == 0))
                    {
                        list.add(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z));
                    }
                }
            }
        }
        return list;
    }

    public static List<BlockPos> getBlocksIn3x1x3Centered(BlockPos pos)
    {
        List<BlockPos> list = new ArrayList<BlockPos>();
        for (int z = -1; z < 2; z++)
        {
            for (int x = -1; x < 2; x++)
            {
                list.add(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z));
            }
        }
        return list;
    }

    public static List<BlockPos> getBlocksIn3x2x2Centered(BlockPos pos, Direction facing)
    {
        List<BlockPos> list = new ArrayList<BlockPos>();
        boolean isSided = facing == Direction.EAST || facing == Direction.WEST;
        boolean invert = facing == Direction.NORTH || facing == Direction.WEST;
        for (int y = 0; y < 2; y++)
        {
            for (int z = 0; z < 2; z++)
            {
                for (int x = -1; x < 2; x++)
                {
                    int finalX = (isSided ? z : x);
                    int finalZ = (isSided ? x : z);
                    list.add(new BlockPos(pos.getX() + (invert ? -finalX : finalX), pos.getY() + y, pos.getZ() + (invert ? -finalZ : finalZ)));
                }
            }
        }
        return list;
    }
}