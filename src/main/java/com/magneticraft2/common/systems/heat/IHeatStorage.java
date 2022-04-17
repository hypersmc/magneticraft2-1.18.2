package com.magneticraft2.common.systems.heat;

public interface IHeatStorage {
    int receiveHeat(int maxReceive, boolean simulate);
    int extractHeat(int maxExtract, boolean simulate);
    int getHeatStored();
    int getMaxHeatStored();
    boolean canSend();
    boolean canReceive();
}
