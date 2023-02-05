package com.magneticraft2.common.utils;

import net.minecraft.core.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JumpWatch on 04-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class BlockPosUtils {
    public static final Logger LOGGER = LogManager.getLogger("MGC2BlockPosUtils");
    public static List<BlockPos> getBetweenPoints(BlockPos start, BlockPos end) {
        LOGGER.info("Start: " + start + " End: " + end);
        int x0 = start.getX();
        int y0 = start.getY();
        int z0 = start.getZ();
        int x1 = end.getX();
        int y1 = end.getY();
        int z1 = end.getZ();

        List<BlockPos> points = new ArrayList<>();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int dz = Math.abs(z1 - z0);

        int x = x0;
        int y = y0;
        int z = z0;

        int x_inc = (x1 > x0) ? 1 : -1;
        int y_inc = (y1 > y0) ? 1 : -1;
        int z_inc = (z1 > z0) ? 1 : -1;

        int error = dx - dz;
        int error2;

        for (int i = 0; i < dx + dz; i++) {
            points.add(new BlockPos(x, y, z));

            error2 = error * 2;

            if (error2 > -dz) {
                error -= dz;
                x += x_inc;
            }
            if (error2 < dx) {
                error += dx;
                z += z_inc;
            }
        }
        LOGGER.info("Points: " + points);
        return points;
    }
}
