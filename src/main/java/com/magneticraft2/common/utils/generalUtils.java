package com.magneticraft2.common.utils;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class generalUtils {
    private static final Random RANDOM = new Random();
    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape)
    {
        if (isY(from) || isY(to)) { throw new IllegalArgumentException("Invalid Direction!"); }
        if (from == to) { return shape; }

        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };

        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++)
        {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(
                    buffer[1],
                    Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)
            ));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }
    public static void sendConsoleMessage(String str)
    {
        System.out.println(str);
    }
    public static void sendChatMessage(Player player, String str)
    {
        if (player == null) Minecraft.getInstance().player.sendMessage(new TextComponent(str), player.getUUID());
        else player.sendMessage(new TextComponent(str), player.getUUID());
    }

    public static float normalizeClamped(float value, float min, float max)
    {
        return Mth.clamp((value - min), 0, (max - min)) / (max - min);
    }
    public static int getDistancePointToPoint(BlockPos pos1, BlockPos pos2)
    {
        int deltaX = pos1.getX() - pos2.getX();
        int deltaY = pos1.getY() - pos2.getY();
        int deltaZ = pos1.getZ() - pos2.getZ();
        return (int) Mth.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }
    public static void spawnItemStack(Level worldIn, BlockPos pos, ItemStack stack)
    {
        if (worldIn.isClientSide) return;
        float f = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty())
        {
            ItemEntity entityitem = new ItemEntity(worldIn, pos.getX() + (double) f, pos.getY() + (double) f1, pos.getZ() + (double) f2, stack.split(RANDOM.nextInt(21) + 10));
            worldIn.addFreshEntity(entityitem);
        }
    }

    public static boolean isPositive(Direction dir) { return dir.getAxisDirection() == Direction.AxisDirection.POSITIVE; }

    public static boolean isX(Direction dir) { return dir.getAxis() == Direction.Axis.X; }

    public static boolean isY(Direction dir) { return dir.getAxis() == Direction.Axis.Y; }

    public static boolean isZ(Direction dir) { return dir.getAxis() == Direction.Axis.Z; }
}
