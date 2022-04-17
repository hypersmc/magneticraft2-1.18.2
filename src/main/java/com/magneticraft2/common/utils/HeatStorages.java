package com.magneticraft2.common.utils;

import com.magneticraft2.common.systems.heat.HeatStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class HeatStorages extends HeatStorage implements INBTSerializable<CompoundTag> {
    public HeatStorages(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }
    protected void onHeatChanged() {
    }
    public void setHeat(int heat) {
        this.heat = heat;
        onHeatChanged();
    }
    public void addHeat(int heat) {
        this.heat += heat;
        if (this.heat > getMaxHeatStored()) {
            this.heat = getHeatStored();
        }
        onHeatChanged();
    }
    public void consumeHeat(int heat) {
        this.heat -= heat;
        if (this.heat < 0) {
            this.heat = 0;
        }
        onHeatChanged();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("heat", getHeatStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setHeat(nbt.getInt("heat"));
    }
}
