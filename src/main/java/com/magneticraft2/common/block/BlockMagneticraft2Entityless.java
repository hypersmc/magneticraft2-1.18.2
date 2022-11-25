package com.magneticraft2.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class BlockMagneticraft2Entityless extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static BooleanProperty assembled = BooleanProperty.create("assembled");
    public static BooleanProperty iscore = BooleanProperty.create("iscore");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final Logger LOGGER = LogManager.getLogger();
    public BlockPattern pattern;


    public BlockMagneticraft2Entityless(Properties p_49795_) {
        super(p_49795_);
    }





}
