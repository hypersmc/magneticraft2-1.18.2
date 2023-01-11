package com.magneticraft2.common.tile.wire;

import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.tile.BlockEntityMultiBlockBase;
import com.magneticraft2.common.utils.IConnectorHV;
import com.magneticraft2.common.utils.generalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BlockEntityTransformerHV extends BlockEntityMultiBlockBase<BlockEntityTransformerHV> implements IConnectorHV {
    IConnectorHV othersideTransformer;
    public boolean isOutPut;
    private BlockPos cableConnectionPos;
    private int energyTransfer;
    private boolean oldOutPut;
    private boolean isConnected;
    private static BlockEntityTransformerHV BlockEntityTransformerHV;
    private int averageEnergy;
    private int oldEnergy;
    private int tick;
    private boolean inUse = false;
    public BlockEntityTransformerHV(BlockPos pos, BlockState state) {
        super(FinalRegistry.Tile_HVTransformer.get(), pos, state);
    }

    private void isOutPut(){
        Direction facing = getMasterFacing();
        BlockPos postPort = worldPosition.offset(facing.getClockWise().getNormal()).offset(facing.getOpposite().getNormal()).below();
        isOutPut = (level.hasNeighborSignal(postPort) || level.hasNeighborSignal(postPort.offset(facing.getClockWise().getNormal())));
        if (isOutPut != oldOutPut){
            oldOutPut = isOutPut;
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 2);
            this.sync();
            this.CheckIfNeedsNetworkRefresh();
        }
    }
    private void CheckIfNeedsNetworkRefresh(){
        if (isMaster() && isConnected()){
            BlockEntity be = level.getBlockEntity(cableConnectionPos);
            if (be instanceof IConnectorHV){
                if ((isOutPut && !((IConnectorHV) be).isOutput()) || (!isOutPut && ((IConnectorHV) be).isOutput())){
                    setOtherSideTransformer((IConnectorHV) be);
                    ((IConnectorHV) be).setOtherSideTransformer(this);
                }else{
                    setOtherSideTransformer(null);
                    ((IConnectorHV) be).setOtherSideTransformer(null);
                }
            }else if (be instanceof BlockEntityHVConnectorBase){
                ((BlockEntityHVConnectorBase) be).forceRecheck();
            }
        }
    }
    public int onEnergyReceived(int maxReceive, boolean simulate){
        if (maxReceive <= 0) return 0;
        if (inUse) return 0;
        inUse = true;
        int out = 0;
        if (!isOutPut){
            if (othersideTransformer != null){
                out = othersideTransformer.receiveEnergy(Math.min(maxReceive, maxtransferE()), simulate);
            }
        }else {
            BlockPos outPutPos = worldPosition.offset(getMasterFacing().getClockWise().getNormal()).below();
            BlockEntity outBlockEntity = level.getBlockEntity(outPutPos);
            if (outBlockEntity != null){
                IEnergyStorage outPutStorage = (IEnergyStorage) outBlockEntity.getCapability(CapabilityEnergy.ENERGY, getMasterFacing());
                if (outPutStorage != null && outPutStorage.canReceive()){
                    out = outPutStorage.receiveEnergy(maxReceive, simulate);
                }
            }
        }
        if (!simulate){
            energyTransfer += out;
        }
        inUse = false;
        return out;
    }
    public static <E extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, E e) {
        if (level.isClientSide && BlockEntityTransformerHV.isMaster()){
            if (BlockEntityTransformerHV.tick >= 10){
                BlockEntityTransformerHV.tick = 0;
                BlockEntityTransformerHV.isOutPut();
                BlockEntityTransformerHV.averageEnergy = BlockEntityTransformerHV.energyTransfer / 10;
                BlockEntityTransformerHV.energyTransfer = 0;
                if (BlockEntityTransformerHV.averageEnergy != BlockEntityTransformerHV.oldEnergy){
                    BlockEntityTransformerHV.oldEnergy = BlockEntityTransformerHV.averageEnergy;
                    BlockEntityTransformerHV.sync();
                }
            }
            BlockEntityTransformerHV.tick++;
        }
    }


    @Override
    public boolean isOutput() {
        return getMaster().isOutPut;
    }

    @Override
    public BlockPos getConnectionPos() {
        return getMaster().cableConnectionPos;
    }

    @Override
    public void setConnectionPos(BlockPos endPos) {
        getMaster().cableConnectionPos = endPos;
        getMaster().isConnected = true;
        if (level.getBlockEntity(endPos) instanceof IConnectorHV){
            getMaster().CheckIfNeedsNetworkRefresh();
        }
        getMaster().sync();
    }

    @Override
    public BlockPos getConnectorPos() {
        return getMaster() != null ? getMaster().getThisPosition().above() : worldPosition;
    }

    @Override
    public void setOtherSideTransformer(IConnectorHV transformer) {
        getMaster().othersideTransformer = transformer;
        getMaster().sync();
    }

    @Override
    public boolean isConnected() {
        return getMaster().isConnected;
    }

    @Override
    public int receiveEnergy(int quantity, boolean simulate) {
        if (isRemoved()) return 0;
        return getMaster().receiveEnergy(quantity, simulate);
    }

    @Override
    public void removeConnection() {
        if (isConnected()){
            getMaster().isConnected = false;
            getMaster().cableConnectionPos = null;
            getMaster().othersideTransformer = null;
            getMaster().sync();
        }
    }

    @Override
    public boolean instanceOf(BlockEntity blockEntity) {
        return blockEntity instanceof BlockEntityTransformerHV;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        if (cableConnectionPos != null){
            tag.putLong("conP", cableConnectionPos.asLong());
        }else {
            tag.putLong("conP", 0L);
        }
        tag.putBoolean("connected", isConnected);
        tag.putBoolean("isOutPut", isOutPut);
        tag.putInt("energy_average", averageEnergy);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        long conL = tag.getLong("conP");
        if (conL != 0L) cableConnectionPos = null;
        else cableConnectionPos = BlockPos.of(conL);
        isConnected = tag.getBoolean("connected");
        isOutPut = tag.getBoolean("isOutPut");
        averageEnergy = tag.getInt("energy_average");
        super.load(tag);
    }

    @Override
    public void onMasterBreak() {
        if (isConnected()) {
            removeConnection();
        }
    }

    private void disableConnectedCables(BlockPos pos){
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityHVConnectorBase){
            ((BlockEntityHVConnectorBase) be).removeConnection(getConnectionPos());
        }else if (be instanceof IConnectorHV){
            ((IConnectorHV) be).removeConnection();
        }
    }
    public void RemoveConnectionAndSpawn(){
        BlockPos connectionPos = getConnectionPos();
        if (!level.isClientSide) {
            generalUtils.spawnItemStack(level, worldPosition, new ItemStack(FinalRegistry.HV_CABLE.get(), 1));
            disableConnectedCables(cableConnectionPos);
            removeConnection();
        }
    }

    @Override
    public int capacityE() {
        return 0;
    }

    @Override
    public int maxtransferE() {
        return 0;
    }

    @Override
    public int capacityH() {
        return 0;
    }

    @Override
    public int maxtransferH() {
        return 0;
    }

    @Override
    public int capacityW() {
        return 0;
    }

    @Override
    public int maxtransferW() {
        return 0;
    }

    @Override
    public int capacityF() {
        return 0;
    }

    @Override
    public int tanks() {
        return 0;
    }

    @Override
    public int invsize() {
        return 0;
    }

    @Override
    public int capacityP() {
        return 0;
    }

    @Override
    public int maxtransferP() {
        return 0;
    }

    @Override
    public boolean itemcape() {
        return false;
    }

    @Override
    public boolean energycape() {
        return false;
    }

    @Override
    public boolean heatcape() {
        return false;
    }

    @Override
    public boolean wattcape() {
        return false;
    }

    @Override
    public boolean fluidcape() {
        return false;
    }

    @Override
    public boolean pressurecape() {
        return false;
    }

    @Override
    public boolean HeatCanReceive() {
        return false;
    }

    @Override
    public boolean HeatCanSend() {
        return false;
    }

    @Override
    public boolean WattCanReceive() {
        return false;
    }

    @Override
    public boolean WattCanSend() {
        return false;
    }

    @Override
    public boolean EnergyCanReceive() {
        return false;
    }

    @Override
    public boolean EnergyCanSend() {
        return false;
    }

    @Override
    public boolean PressureCanReceive() {
        return false;
    }

    @Override
    public boolean PressureCanSend() {
        return false;
    }

    @Override
    public Level getThisWorld() {
        return null;
    }

    @Override
    public BlockPos getThisPosition() {
        return worldPosition;
    }

    @Override
    public CompoundTag sync() {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
