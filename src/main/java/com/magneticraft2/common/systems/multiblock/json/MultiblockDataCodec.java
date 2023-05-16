package com.magneticraft2.common.systems.multiblock.json;

import com.google.gson.*;
import net.minecraft.world.level.block.Block;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JumpWatch on 16-05-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class MultiblockDataCodec implements JsonDeserializer<MultiblockData> {
    @Override
    public MultiblockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract properties from the JsonObject
        String name = jsonObject.get("name").getAsString();
        MultiblockStructure structure = context.deserialize(jsonObject.get("structure"), MultiblockStructure.class);
        JsonObject blocksObject = jsonObject.getAsJsonObject("blocks");
        Map<String, Block> blocks = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : blocksObject.entrySet()) {
            String blockId = entry.getKey();
            Block block = context.deserialize(entry.getValue(), Block.class);
            blocks.put(blockId, block);
        }
        MultiblockSettings settings = context.deserialize(jsonObject.get("settings"), MultiblockSettings.class);

        // Create and return the MultiblockData instance
        return new MultiblockData(name, structure, blocks, settings);
    }
}