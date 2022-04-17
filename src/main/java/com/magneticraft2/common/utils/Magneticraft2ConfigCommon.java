package com.magneticraft2.common.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class Magneticraft2ConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> example_integer;
    public static final ForgeConfigSpec.ConfigValue<String> example_string;

    static {
        BUILDER.push("Configuration for Magneticraft 2");
        example_integer = BUILDER.comment("This is an integer. Default value is 3.").define("Example Integer", 3);
        example_string = BUILDER.comment("This is a string. Default value is \"Cy4\".").define("Example String", "Cy4");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
