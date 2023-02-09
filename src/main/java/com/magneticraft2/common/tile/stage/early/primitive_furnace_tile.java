package com.magneticraft2.common.tile.stage.early;

import com.magneticraft2.client.gui.container.primitive_furnace.containerPrimitive_Furnace;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.multiblock.CustomBlockPattern;
import com.magneticraft2.common.systems.multiblock.Multiblock;
import com.magneticraft2.common.block.stage.early.primitive_furnace_block;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static com.magneticraft2.common.tile.Multiblockfiller_tile.setMulitblockListener;


/**
 * @author JumpWatch on 26-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class primitive_furnace_tile extends Multiblock implements MenuProvider {
    private static primitive_furnace_tile self;
    private static ResourceLocation  id;
    private static final Logger LOGGER = LogManager.getLogger(id);
    public primitive_furnace_tile(BlockPos pos, BlockState state) {
        super(FinalRegistry.primitive_furnace_Tile.get(), pos, state, CustomBlockPattern.builder()
                .row("s")
                .row("C")
                .row("s")
                .where('s', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.STONE)))
                .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(FinalRegistry.primitive_furnace_Block.get().defaultBlockState().getBlock())))
                .build());
        menuProvider = this;
        self = this;
    }


    public static <E extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState estate, E e) {
        if (!level.isClientSide()) {

//            if (!self.isFormed()) {
//                self.tick(level);
//                estate = estate.setValue(primitive_furnace_block.isFormed, false);
//            }else{
//                estate = estate.setValue(primitive_furnace_block.isFormed, true);
//
//            }
////            level.addParticle(primitive_furnace_block.particle, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
//            level.setBlockAndUpdate(pos, estate);
        }

    }



    @Override
    public int invsize() {
        return 4;
    }

    @Override
    public boolean itemcape() {
        return true;
    }

    @Override
    public void assignID(Level world, BlockPos pos) {
        id = new ResourceLocation(magneticraft2.MOD_ID, "primitive_furnace_tile_" + UUID.randomUUID().toString());
        LOGGER.info("ID: " + id);
    }

    @Override
    public Block getReplacementBlock() {
        return FinalRegistry.Block_Multiblock_filler.get();
    }

    @Override
    public ResourceLocation getID() {
        return id;
    }

    @Override
    public boolean canBeRotated() {
        return true;
    }

    @Override
    public boolean canBeMirrored() {
        return false;
    }




    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("screen.magneticraft2.primitive_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new containerPrimitive_Furnace(pContainerId, level, getBlockPos(), pPlayerInventory, pPlayer);
    }
}
