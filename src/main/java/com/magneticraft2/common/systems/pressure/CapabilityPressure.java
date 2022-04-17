package com.magneticraft2.common.systems.pressure;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityPressure {
    public static final Capability<IPressureStorage> PRESSURE = CapabilityManager.get(new CapabilityToken<>(){});;
    public static void register(RegisterCapabilitiesEvent event) { event.register(IPressureStorage.class);}

}
