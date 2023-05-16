package com.magneticraft2.common.tile;

/**
 * @author JumpWatch on 23-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class testblock{ //extends Multiblock implements MenuProvider {
//
//    public static testblock self;
//    public testblock(BlockPos pos, BlockState state) {
//        super(FinalRegistry.Tile_testblock.get(), pos, state, CustomBlockPattern.builder()
//                .row(" s ")
//                .row(" c ")
//                .row(" s ")
//                .where('s', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.STONE)))
//                .where('c',BlockInWorld.hasState(statee -> statee.getBlock() == FinalRegistry.Block_testblock.get())
//                )
//                .build());
//        menuProvider = this;
//        self = this;
//    }
//
//
//
//    @Override
//    public void assignID(Level world, BlockPos pos) {
//
//    }
//
//    @Override
//    public Block getReplacementBlock() {
//        return FinalRegistry.Block_Multiblock_filler.get();
//    }
//
//    public static <E extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, E e) {
//        if (!level.isClientSide()) {
//
//            if (!self.isFormed()) {
//                self.tick(level);
//            }
//        }
//
//    }
//
//
//
//    @Override
//    public ResourceLocation getID() {
//        return null;
//    }
//
//    @Override
//    public boolean canBeRotated() {
//        return false;
//    }
//
//    @Override
//    public boolean canBeMirrored() {
//        return false;
//    }
//
//    @Override
//    public Component getDisplayName() {
//        return new TranslatableComponent("screen.magneticraft2.testblock");
//    }
//
//    @Nullable
//    @Override
//    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
//        return null;
//    }
}
