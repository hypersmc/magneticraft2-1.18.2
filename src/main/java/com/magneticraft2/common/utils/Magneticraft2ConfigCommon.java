package com.magneticraft2.common.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class Magneticraft2ConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);




    public static class General {
        //From Cuprum mod made by me
        public  final ForgeConfigSpec.ConfigValue<Integer> MaxWireLenght;
//        public  final ForgeConfigSpec.ConfigValue<Integer> EnderCooldown;
//        public  final ForgeConfigSpec.ConfigValue<Double> EnderDamage;
        General(ForgeConfigSpec.Builder builder) {
            builder.push("Configuration for Magneticraft2");
            MaxWireLenght = builder
                    .comment("The max length Wires can go")
                    .translation("magneticraft2.wirelenght.config")
                    .defineInRange("Wire Length", 64, 0, Integer.MAX_VALUE);
//            EnderCooldown = builder
//                    .comment("The Cooldown before Stable Ender Pearl can be used again (In ticks). Default value is 4.")
//                    .translation("cuprum.endercooldown.config")
//                    .defineInRange("Ender Cooldown", 160, 0, Integer.MAX_VALUE);
//            EnderDamage = builder
//                    .comment("The damage the player takes when landing. Default value is 0.5.")
//                    .translation("cuprum.enderdamage.config")
//                    .defineInRange("Ender Damage", 0.5, 0.0, 20.0);
            builder.pop();
        }


    }
    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
