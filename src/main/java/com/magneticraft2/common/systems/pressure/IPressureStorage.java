package com.magneticraft2.common.systems.pressure;

public interface IPressureStorage {
    int receivePressure(int maxReceive, boolean simulate);
    int extractPressure(int maxExtract, boolean simulate);
    int getPressureStored();
    int getMaxPressureStored();
    boolean canSend();
    boolean canReceive();
}
