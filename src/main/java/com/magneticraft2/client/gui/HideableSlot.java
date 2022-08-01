package com.magneticraft2.client.gui;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class HideableSlot extends Slot {
    final Slot target;
    private boolean active = true;

    public HideableSlot(Slot pSlot, int pIndex, int pX, int pY, boolean active) {
        super(pSlot.container, pIndex, pX, pY);
        this.target = pSlot;
        this.active = active;
    }


    @Override
    public boolean isActive() {
        return false;
    }
}
