package com.magneticraft2.client.gui;

import com.magneticraft2.common.magneticraft2;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class MoveableSlot extends Slot {


    public MoveableSlot(Slot inv, int index, int xPosition, int yPosition) {
        super(inv.container, index, xPosition, yPosition);

    }
    public void move(int x1, int y1){
        try {
            ObfuscationReflectionHelper.findField(Slot.class, "xPos").setInt(this, x+x1);
            ObfuscationReflectionHelper.findField(Slot.class, "yPos").setInt(this, y+y1);
        } catch (Exception e) {
            magneticraft2.LOGGER.error("Could not move slot! ReflectionError");
            throw new RuntimeException("Reflection Error while moving slot.");
        }
    }
}