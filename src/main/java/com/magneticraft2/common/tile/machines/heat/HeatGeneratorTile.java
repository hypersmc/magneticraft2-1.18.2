package com.magneticraft2.common.tile.machines.heat;

import com.magneticraft2.client.gui.container.ContainerHeatGenerator;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.magneticraft2.common.systems.heat.BiomHeatHandling.getHeatManagment;

public class HeatGeneratorTile extends TileEntityMagneticraft2 {
    boolean alreadyset = false;

    public HeatGeneratorTile(BlockPos pos, BlockState state) {
        super(FinalRegistry.Tile_Heat_Generator.get(), pos, state);
        menuProvider = this;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putBoolean("set", alreadyset);
        super.saveAdditional(tag);
    }

    @Override
    public void tick() {
        if (!level.isClientSide){
            if (!alreadyset) {
                //Here we set the default
                this.addHeatToStorage(getHeatManagment(level, getBlockPos(), "start"));
                alreadyset = true;
            }
            if (getEnergyStorage() < 1) {
                if (level.getGameTime() % 15 == 0) {
                    if (this.getHeatStorage() > getHeatManagment(level, getBlockPos(), "min")) {
                        this.removeHeatFromStorage(getHeatManagment(level, getBlockPos(), "losetick"));
                    }
                }
                return;
            }

            if (this.getHeatStorage() >= this.getMaxHeatStorage()){
                this.setHeatHeat(this.getMaxHeatStorage());
                this.removeHeatFromStorage(getHeatManagment(level, getBlockPos(), "losetick"));
            }else{
                this.removeEnergyFromStorage(300);
                this.addHeatToStorage(getHeatManagment(level, getBlockPos(), "gain"));
            }
        }
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("screen.magneticraft2.heatgenerator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory playerinv, Player player) {
        return new ContainerHeatGenerator(i,level,getBlockPos(),playerinv,player);
    }


    @Override
    public int capacityE() {
        return 3000;
    }

    @Override
    public int maxtransferE() {
        return 200;
    }

    @Override
    public int capacityH() {
        return 100;
    }

    @Override
    public int maxtransferH() {
        return 200;
    }

    @Override
    public int capacityW() {
        return 0;
    }

    @Override
    public int maxtransferW() {
        return 0;
    }

    @Override
    public int capacityF() {
        return 0;
    }

    @Override
    public int tanks() {
        return 0;
    }

    @Override
    public int invsize() {
        return 0;
    }

    @Override
    public int capacityP() {
        return 0;
    }

    @Override
    public int maxtransferP() {
        return 0;
    }

    @Override
    public boolean itemcape() {
        return false;
    }

    @Override
    public boolean energycape() {
        return true;
    }

    @Override
    public boolean heatcape() {
        return true;
    }

    @Override
    public boolean wattcape() {
        return false;
    }

    @Override
    public boolean fluidcape() {
        return false;
    }

    @Override
    public boolean pressurecape() {
        return false;
    }

    @Override
    public boolean HeatCanReceive() {
        return false;
    }

    @Override
    public boolean HeatCanSend() {
        return true;
    }

    @Override
    public boolean WattCanReceive() {
        return false;
    }

    @Override
    public boolean WattCanSend() {
        return false;
    }

    @Override
    public boolean EnergyCanReceive() {
        return true;
    }

    @Override
    public boolean EnergyCanSend() {
        return false;
    }

    @Override
    public boolean PressureCanReceive() {
        return false;
    }

    @Override
    public boolean PressureCanSend() {
        return false;
    }


}
