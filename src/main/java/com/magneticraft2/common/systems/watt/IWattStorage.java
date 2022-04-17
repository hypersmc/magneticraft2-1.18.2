package com.magneticraft2.common.systems.watt;

public interface IWattStorage {
    int receiveWatt(int maxReceive, boolean simulate);
    int extractWatt(int maxExtract, boolean simulate);
    int getWattStored();
    int getMaxWattStored();
    boolean canSend();
    boolean canReceive();
}
