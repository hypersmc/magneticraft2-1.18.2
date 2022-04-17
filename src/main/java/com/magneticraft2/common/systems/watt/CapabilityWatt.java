package com.magneticraft2.common.systems.watt;


import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityWatt {
    public static final Capability<IWattStorage> WATT = CapabilityManager.get(new CapabilityToken<>(){});;
    public static void register(RegisterCapabilitiesEvent event) { event.register(IWattStorage.class);}
}
