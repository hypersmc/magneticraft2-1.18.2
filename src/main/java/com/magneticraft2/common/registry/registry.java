package com.magneticraft2.common.registry;

import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.utils.mbthings.WorkQueue;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class registry {
    private final WorkQueue blockRegistrationQueue = new WorkQueue();
    private RegistryEvent.Register<Block> blockRegistryEvent;

    private final WorkQueue itemRegistrationQueue = new WorkQueue();
    private RegistryEvent.Register<Item> itemRegistryEvent;
    private final CreativeModeTab itemGroup;
    private Item itemGroupItem = Items.STONE;

    private final WorkQueue fluidRegistrationQueue = new WorkQueue();
    private RegistryEvent.Register<Fluid> fluidRegistryEvent;

    private final WorkQueue containerRegistrationQueue = new WorkQueue();
    private RegistryEvent.Register<MenuType<?>> containerRegistryEvent;

    private final WorkQueue tileRegistrationQueue = new WorkQueue();
    private RegistryEvent.Register<BlockEntityType<?>> tileRegistryEvent;
    private final Map<Class<?>, LinkedList<Block>> tileBlocks = new HashMap<>();

    private final WorkQueue clientSetupQueue = new WorkQueue();
    private FMLClientSetupEvent clientSetupEvent;

    private final WorkQueue commonSetupQueue = new WorkQueue();
    private FMLCommonSetupEvent commonSetupEvent;

    private final ArrayList<Runnable> biomeLoadingEventHandlers = new ArrayList<>();
    private BiomeLoadingEvent biomeLoadingEvent;

    private final Map<String, AnnotationHandler> annotationMap = new HashMap<>();
    {
        annotationMap.put(RegisterBlock.class.getName(), this::registerBlockAnnotation);
        annotationMap.put(RegisterTile.class.getName(), this::registerTileAnnotation);
        //annotationMap.put(OnModLoad.class.getName(), this::onModLoadAnnotation);
    }

    private static final Logger LOGGER = LogManager.getLogger();
    public registry() {
        String callerClass = new Exception().getStackTrace()[1].getClassName();
        String callerPackage = callerClass.substring(0, callerClass.lastIndexOf("."));
        String modNamespace = callerPackage.substring(callerPackage.lastIndexOf(".") + 1);
        ModFileScanData modFileScanData = FMLLoader.getLoadingModList().getModFileById(magneticraft2.MOD_ID).getFile().getScanResult();
        itemGroup = FinalRegistry.MC2Blocks;

        // these two are special cases that need to be handled first
        // in case anything needs config options during static construction
        // this is used for module registration, which need to happen before block registration
        handleAnnotationType(modFileScanData, callerPackage, modNamespace, OnModLoad.class.getName(), this::onModLoadAnnotation);


        for (ModFileScanData.AnnotationData annotation : modFileScanData.getAnnotations()) {
            AnnotationHandler handler = annotationMap.get(annotation.annotationType().getClassName());
            if (handler == null) {
                // not an annotation i handle
                continue;
            }
            String className = annotation.clazz().getClassName();
            if (className.startsWith(callerPackage)) {
                try {
                    Class<?> clazz = registry.class.getClassLoader().loadClass(className);
                    // class loaded, so, pass it off to the handler
                    handler.run(modNamespace, clazz, annotation.memberName());
                } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
                }
            }
        }
    }
    private interface AnnotationHandler {
        void run(final String modNamespace, final Class<?> clazz, final String memberName);
    }
    private static void handleAnnotationType(ModFileScanData modFileScanData, String callerPackage, String modNamespace, String name, AnnotationHandler handler) {
        for (ModFileScanData.AnnotationData annotation : modFileScanData.getAnnotations()) {
            if (!annotation.annotationType().getClassName().equals(name)) {
                // not the annotation handled here
                continue;
            }
            String className = annotation.clazz().getClassName();
            if (className.startsWith(callerPackage)) {
                try {
                    Class<?> clazz = registry.class.getClassLoader().loadClass(className);
                    // class loaded, so, pass it off to the handler
                    handler.run(modNamespace, clazz, annotation.memberName());
                } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
                }
            }
        }
    }

    private void onModLoadAnnotation(String modNamespace, Class<?> modLoadClazz, final String memberName) {
        try {
            Method method = modLoadClazz.getDeclaredMethod(memberName.substring(0, memberName.indexOf('(')));
            if(!Modifier.isStatic(method.getModifiers())){
                LOGGER.error("Cannot call non-static @OnModLoad method " + method.getName() + " in " + modLoadClazz.getSimpleName());
                return;
            }

            if(method.getParameterCount() != 0){
                LOGGER.error("Cannot call @OnModLoad method with parameters " + method.getName() + " in " + modLoadClazz.getSimpleName());
                return;
            }

            method.setAccessible(true);
            method.invoke(null);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private static final Field tileProducerTYPEField;
    static {
        try {
            tileProducerTYPEField = RegisterTile.Producer.class.getDeclaredField("TYPE");
            tileProducerTYPEField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }
    private void registerTileAnnotation(String modNamespace, Class<?> declaringClass, final String memberName) {
        tileRegistrationQueue.enqueue(() -> {

            final Field field;
            final RegisterTile annotation;
            final RegisterTile.Producer<?> producer;

            try {
                field = declaringClass.getDeclaredField(memberName);
                if (!field.isAnnotationPresent(RegisterTile.class)) {
                    LOGGER.error("Schrodinger's annotation on field " + memberName + " in " + declaringClass.getSimpleName());
                    return;
                }
                annotation = field.getAnnotation(RegisterTile.class);
                if (field.isAnnotationPresent(IgnoreRegistration.class)) {
                    return;
                }
                field.setAccessible(true);
                var producerObject = field.get(null);
                if (producerObject == null) {
                    LOGGER.error("Null supplier for tile field " + memberName + " in " + declaringClass.getSimpleName());
                    return;
                }
                if (producerObject.getClass() != RegisterTile.Producer.class) {
                    LOGGER.error("Attempt to register non-TileProducer BlockEntitySupplier " + memberName + " in " + declaringClass.getSimpleName());
                    return;
                }
                producer = (RegisterTile.Producer<?>) producerObject;
            } catch (NoSuchFieldException e) {
                LOGGER.error("Unable to find supplier field for tile " + memberName + " in " + declaringClass.getSimpleName());
                return;
            } catch (IllegalAccessException e) {
                LOGGER.error("Unable to access supplier field for tile " + memberName + " in " + declaringClass.getSimpleName());
                return;
            }

            String modid = annotation.modid();
            if (modid.equals("")) {
                modid = modNamespace;
            }
            String name = annotation.value();
            if (modid.equals("")) {
                LOGGER.error("Unable to register tile without a name from " + memberName + " in " + declaringClass.getSimpleName());
                return;
            }
            final String registryName = modid + ":" + name;

            // this is safe, surely
            // should actually be, otherwise previous checks should have errored
            Class<?> tileClass = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

            LinkedList<Block> blocks = tileBlocks.remove(tileClass);

            if (blocks == null) {
                return;
            }

            // fuck you java, its the correct size here
            @SuppressWarnings({"ConstantConditions", "ToArrayCallWithZeroLengthArrayArgument"})
            BlockEntityType<?> type = BlockEntityType.Builder.of(producer, blocks.toArray(new Block[blocks.size()])).build(null);

            type.setRegistryName(registryName);

            try {
                tileProducerTYPEField.set(producer, type);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Tile entity type unable to be saved for " + memberName + " in " + declaringClass.getSimpleName());
            }

            tileRegistryEvent.getRegistry().register(type);
        });
    }
    private void registerBlockAnnotation(final String modNamespace, final Class<?> blockClazz, final String memberName) {
        if (blockClazz.isAnnotationPresent(IgnoreRegistration.class)) {
            return;
        }

        blockRegistrationQueue.enqueue(() -> {
            final Block block;
            final RegisterBlock annotation;
            try {
                final Field field = blockClazz.getDeclaredField(memberName);
                if (field.isAnnotationPresent(IgnoreRegistration.class)) {
                    return;
                }
                field.setAccessible(true);
                block = (Block) field.get(null);
                annotation = field.getAnnotation(RegisterBlock.class);

                if (!Modifier.isFinal(field.getModifiers())) {
                    LOGGER.warn("Non-final block instance variable " + memberName + " in " + blockClazz.getSimpleName());
                }
            } catch (NoSuchFieldException e) {
                LOGGER.error("Unable to find block field for block " + memberName + " in " + blockClazz.getSimpleName());
                return;
            } catch (IllegalAccessException e) {
                LOGGER.error("Unable to access block field for block " + memberName + " in " + blockClazz.getSimpleName());
                return;
            }

            if (block == null) {
                LOGGER.warn("Null block instance variable " + memberName + " in " + blockClazz.getSimpleName());
                return;
            }

            String modid = annotation.modid();
            if (modid.equals("")) {
                modid = modNamespace;
            }
            String name = annotation.name();
            if (modid.equals("")) {
                LOGGER.error("Unable to register block without a name from class " + blockClazz.getSimpleName());
                return;
            }

            if (!Block.class.isAssignableFrom(blockClazz)) {
                LOGGER.error("Attempt to register block from class not extended from Block. " + blockClazz.getSimpleName());
                return;
            }


            final String registryName = modid + ":" + name;

            if (annotation.tileEntityClass() != BlockEntity.class) {
                tileBlocks.computeIfAbsent(annotation.tileEntityClass(), k -> new LinkedList<>()).add(block);
            }

            block.setRegistryName(registryName);
            blockRegistryEvent.getRegistry().register(block);

            if (FMLEnvironment.dist.isClient()) {
                clientSetupQueue.enqueue(() -> {
                    RenderType renderType = null;
                    for (Method declaredMethod : blockClazz.getDeclaredMethods()) {
                        if (declaredMethod.isAnnotationPresent(RegisterBlock.RenderLayer.class)) {
                            if (Modifier.isStatic(declaredMethod.getModifiers())) {
                                LOGGER.error("Cannot call static render layer method " + declaredMethod.getName() + " in " + blockClazz.getSimpleName());
                                continue;
                            }
                            if (!RenderType.class.isAssignableFrom(declaredMethod.getReturnType())) {
                                LOGGER.error("RenderLayer annotated method " + declaredMethod.getName() + " in " + blockClazz.getSimpleName() + " does not return RenderType");
                                continue;
                            }
                            if (declaredMethod.getParameterCount() != 0) {
                                LOGGER.error("RenderLayer annotated method " + declaredMethod.getName() + " in " + blockClazz.getSimpleName() + " requires arguments");
                                continue;
                            }
                            if (renderType != null) {
                                LOGGER.error("Duplicate RenderLayer methods in " + blockClazz.getSimpleName());
                                continue;
                            }
                            declaredMethod.setAccessible(true);
                            try {
                                Object obj = declaredMethod.invoke(block);
                                renderType = (RenderType) obj;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                                continue;
                            }
                            final RenderType finalRenderType = renderType;
                            // parallel dispatch, and non-synchronized
                            clientSetupEvent.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(block, finalRenderType));
                        }
                    }
                });
            }

            if (annotation.registerItem()) {

                boolean creativeTabBlock = blockClazz.isAnnotationPresent(CreativeTabBlock.class);
                itemRegistrationQueue.enqueue(() -> {
                    //noinspection ConstantConditions
                    var item = new BlockItem(block, new Item.Properties().tab(annotation.creativeTab() ? itemGroup : null /* its fine */)).setRegistryName(registryName);
                    itemRegistryEvent.getRegistry().register(item);
                    if (creativeTabBlock) {
                        itemGroupItem = item;
                    }
                });
            }
        });
    }
}
