package com.magneticraft2.common.systems.multiblock.json;


import com.google.gson.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * @author JumpWatch on 13-05-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class MultiblockManager {
    private static final Gson gson = new Gson();
    protected static final String JSON_EXTENSION = ".json";
    protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();
    public static final Logger LOGGER = LogManager.getLogger("Magneticraft2 Multiblock handler");
    public static void loadMultiblocks(String modid, ResourceManager resourceManager) {
        LOGGER.info("Started to register multiblocks for mod " + modid);

        for (ResourceLocation resourceLocation : resourceManager.listResources("multiblocks", file -> file.endsWith(".json"))){
            final String folderName = "multiblocks";
            final String namespace = resourceLocation.getNamespace();
            final String filePath = resourceLocation.getPath();
            final String dataPath = filePath.substring(folderName.length() + 1, filePath.length() - ".json".length());
            final ResourceLocation jsonIdentifier = new ResourceLocation(namespace, dataPath);

            try (InputStream inputStream = resourceManager.getResource(resourceLocation).getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                // Parse JSON into JsonElement
                JsonElement jsonElement = JsonParser.parseReader(reader);
                LOGGER.info("Trying to make MultiblockDataCodec from :" + inputStream + " This may fail ");
                // Create custom Gson instance with the custom Codec
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(MultiblockData.class, new MultiblockDataCodec())
                        .create();

                // Decode the JsonElement into MultiblockData using the custom Codec
                try {
                    MultiblockData multiblockData = gson.fromJson(jsonElement, MultiblockData.class);
                    // Register the blocks used in the multiblock
                    Map<String, Block> blocks = new HashMap<>();
                    for (Map.Entry<String, Block> entry : multiblockData.getBlocks().entrySet()) {
                        ResourceLocation blockRL = new ResourceLocation(entry.getKey());
                        Block block = Registry.BLOCK.get(blockRL);
                        if (block == null) {
                            throw new IllegalArgumentException("Invalid block resource location: " + blockRL);
                        }
                        blocks.put(entry.getKey(), block);
                    }

                    // Create the multiblock object
                    Multiblock multiblock = new Multiblock(
                            multiblockData.getName(),
                            multiblockData.getStructure(),
                            blocks,
                            multiblockData.getSettings()
                    );

                    // Register the multiblock
                    MultiblockRegistry.registerMultiblock(modid, multiblock);
                } catch (JsonSyntaxException e) {
                    LOGGER.info("could not load: " + resourceLocation.getNamespace());
                    throw new RuntimeException(e);
                }

            } catch (IOException e) {
                throw new RuntimeException("Failed to read multiblock data from " + jsonIdentifier, e);
            }
        }
    }
}