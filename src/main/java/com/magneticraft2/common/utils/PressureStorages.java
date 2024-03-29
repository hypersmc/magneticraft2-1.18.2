package com.magneticraft2.common.utils;

import com.magneticraft2.common.systems.pressure.PressureStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
@Deprecated(forRemoval = true)
public class PressureStorages extends PressureStorage implements INBTSerializable<CompoundTag> {
    public PressureStorages(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }
    protected void onPressureChanged() {

    }
    public void setPressure(int pressure) {
        this.pressure = pressure;
        onPressureChanged();
    }
    public void addPressure(int pressure) {
        this.pressure += pressure;
        if (this.pressure > getMaxPressureStored()) {
            this.pressure = getPressureStored();
        }
        onPressureChanged();
    }
    public void consumePressure(int pressure) {
        this.pressure -= pressure;
        if (this.pressure < 0) {
            this.pressure = 0;
        }
        onPressureChanged();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("pressure", getPressureStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setPressure(nbt.getInt("pressure"));
    }
}
