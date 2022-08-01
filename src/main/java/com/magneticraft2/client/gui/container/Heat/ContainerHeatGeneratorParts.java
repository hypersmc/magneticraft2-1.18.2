package com.magneticraft2.client.gui.container.Heat;

import com.magneticraft2.common.registry.ContainerAndScreenRegistry;
import com.magneticraft2.common.systems.heat.IHeatStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class ContainerHeatGeneratorParts extends AbstractContainerMenu {
    private BlockEntity tileEntity;
    private Player playerEntity;
    private IHeatStorage heatStorage;
    private IEnergyStorage energyStorage;
    private IItemHandler playerInventory;

    public ContainerHeatGeneratorParts(int windowid, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(ContainerAndScreenRegistry.HEAT_GENERATOR_CONTAINER_PARTS.get(), windowid);
        tileEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(12, 96);
        if (tileEntity != null){
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent( h -> {
                this.addSlot(new SlotItemHandler(h, 0, 0, 0)); //1
                this.addSlot(new SlotItemHandler(h, 1, 0, 0)); //2
                this.addSlot(new SlotItemHandler(h, 2, 0, 0)); //3
                this.addSlot(new SlotItemHandler(h, 3, 0, 0)); //4
            });
        }
        layoutPlayerInventorySlots(10, 70);

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
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
}
