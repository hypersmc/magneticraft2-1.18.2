package com.magneticraft2.client.gui.container;

import com.magneticraft2.common.registry.ContainerAndScreenRegistry;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.heat.IHeatStorage;
import com.magneticraft2.common.utils.EnergyStorages;
import com.magneticraft2.common.utils.HeatStorages;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ContainerHeatGenerator extends AbstractContainerMenu {
    private BlockEntity tileEntity;
    private Player playerEntity;
    private IHeatStorage heatStorage;
    private IEnergyStorage energyStorage;
    private IItemHandler playerInventory;
    public ContainerHeatGenerator(int windowid, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(ContainerAndScreenRegistry.HEAT_GENERATOR_CONTAINER.get(), windowid);
        tileEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(12, 96);
        trackPower();
        trackHeat();
    }
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, FinalRegistry.Block_Heat_Generator.get());
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow){
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
    public void trackPower() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((EnergyStorages)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((EnergyStorages)h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }
    public void trackHeat() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getHeat() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityHeat.HEAT).ifPresent(h -> {
                    int heatStored = h.getHeatStored() & 0xffff0000;
                    ((HeatStorages)h).setHeat(heatStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getHeat() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityHeat.HEAT).ifPresent(h -> {
                    int heatStored = h.getHeatStored() & 0x0000ffff;
                    ((HeatStorages)h).setHeat(heatStored | (value << 16));
                });
            }
        });
    }
    public BlockEntity getTileEntity(){
        return tileEntity;
    }
    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }
    public int getEnergylimit(){
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }


    public int getHeat(){
        return tileEntity.getCapability(CapabilityHeat.HEAT).map(IHeatStorage::getHeatStored).orElse(0);
    }
    public int getHeatLimit(){
        return tileEntity.getCapability(CapabilityHeat.HEAT).map(IHeatStorage::getMaxHeatStored).orElse(0);
    }
}
