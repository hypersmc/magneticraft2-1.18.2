package com.magneticraft2.common.utils;

import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IModularTile {

    default <Type> Type as(Class<Type> clazz) {
        //noinspection unchecked
        return (Type) this;
    }


    TileModule<?> module(Class<?> interfaceClazz);

    default <T extends TileModule<?>> T module(Class<?> interfaceClazz, Class<T> moduleType) {
        //noinspection unchecked
        return (T) module(interfaceClazz);
    }

    List<TileModule<?>> modules();
}