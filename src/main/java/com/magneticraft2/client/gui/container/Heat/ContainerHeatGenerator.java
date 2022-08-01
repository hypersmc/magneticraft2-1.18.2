package com.magneticraft2.client.gui.container.Heat;

import com.magneticraft2.client.gui.HideableSlot;
import com.magneticraft2.client.gui.MoveableSlot;
import com.magneticraft2.common.registry.ContainerAndScreenRegistry;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.heat.IHeatStorage;
import com.magneticraft2.common.utils.EnergyStorages;
import com.magneticraft2.common.utils.HeatStorages;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ContainerHeatGenerator extends AbstractContainerMenu {
    public BlockEntity tileEntity;
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
//        if (tileEntity != null) {
//            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
//                this.addSlot(new SlotItemHandler(h, 0, 999999, 999999)); //1
//                this.addSlot(new SlotItemHandler(h, 1, 999999, 999999)); //2
//                this.addSlot(new SlotItemHandler(h, 2, 999999, 999999)); //3
//                this.addSlot(new SlotItemHandler(h, 3, 999999, 999999)); //4
//            });
//        }
    }
    public BlockEntity getTile(){
        return tileEntity;
    }

    public void addslot(int index, int xpos, int ypos){
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                this.addSlot(new SlotItemHandler(h, index, xpos, ypos)); //1
            });
        }
    }
    public void moveSlot(Slot con, int index, int xpos, int ypos){
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                this.addSlot(new MoveableSlot(con, index, xpos, ypos)); //1
            });
        }
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

    public int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
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

    public boolean getParts(){
        return tileEntity.getTileData().getBoolean("settings");
    }
    public void setParts(boolean var){
        tileEntity.getTileData().putBoolean("settings", var);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {


                if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }else {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
