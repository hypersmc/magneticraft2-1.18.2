package com.magneticraft2.common.tile;

import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.multiblock.CustomBlockPattern;
import com.magneticraft2.common.systems.multiblock.Multiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * @author JumpWatch on 23-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class testblock extends Multiblock implements MenuProvider {


    public testblock(BlockPos pos, BlockState state) {
        super(FinalRegistry.Tile_testblock.get(), pos, state, CustomBlockPattern.builder()
                .row(" # ")
                .row(" c ")
                .row(" # ")
                .where('#', states -> states.getBlock() == Blocks.STONE)
                .where('c', states -> states.getBlock() == FinalRegistry.Block_testblock.get())
                .build());
        menuProvider = this;
    }


    @Override
    public void assignID(Level world, BlockPos pos) {

    }

    @Override
    public Block getReplacementBlock() {
        return FinalRegistry.Block_Multiblock_filler.get();
    }

    @Override
    public <E extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, E e) {
        if (!isFormed()){
            tick(level);
        }

    }



    @Override
    public Identifier getID() {
        return null;
    }

    @Override
    public boolean canBeRotated() {
        return true;
    }

    @Override
    public boolean canBeMirrored() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("screen.magneticraft2.testblock");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }
}
