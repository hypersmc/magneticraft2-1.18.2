package com.magneticraft2.common.item.stage.early.pots;

import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.tile.stage.early.PitKilnBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * @author JumpWatch on 08-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class clayPot extends Item {
    public clayPot() {
        super(new Properties().stacksTo(1).setNoRepair().tab(FinalRegistry.MC2Items));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        // Check that the player is sneaking
        if (player.isCrouching()) {
            // Get the block position below the player
            BlockPos pos = new BlockPos(player.getX(), player.getY() - 1, player.getZ());
            // Check that the block below the player is air and the block below that is solid
            if (world.isEmptyBlock(pos) && world.getBlockState(pos.below()).getBlock() != Blocks.AIR) {
                // Place the pit kiln block at the lower position
                world.setBlock(pos, FinalRegistry.PitKilnblock.get().defaultBlockState(), 3);

                // Get the block entity and item handler for the pit kiln
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    IItemHandler itemHandler = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
                    // Try to insert the held item into the next available slot
                    for (int i = 2; i <= 5; i++) {
                        if (itemHandler.getStackInSlot(i).isEmpty()) {
                            itemHandler.insertItem(i, heldStack.copy().split(1), false);
                            heldStack.shrink(1);
                            break;
                        }
                    }
                }
                // Return a success result with the modified held stack
                return InteractionResultHolder.success(heldStack);
            }
        }
        // Return a pass result with the original held stack
        return InteractionResultHolder.pass(heldStack);
    }
}
