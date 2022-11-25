package com.magneticraft2.compatibility.JEI;

import com.magneticraft2.common.magneticraft2;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
@JeiPlugin
public class MGC2JEIAddon implements IModPlugin {
    public static IJeiHelpers jeiHelpers = null;
    public static IJeiRuntime jeiRuntime = null;


    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IRecipeTransferHandlerHelper transferHelper = registration.getTransferHelper();
        IStackHelper stackHelper = jeiHelpers.getStackHelper();

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime ijeiRuntime) {
        jeiRuntime = ijeiRuntime;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(magneticraft2.MOD_ID, "jei_plugin");
    }
}
