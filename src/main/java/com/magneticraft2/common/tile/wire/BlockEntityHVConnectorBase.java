package com.magneticraft2.common.tile.wire;

import com.magneticraft2.common.block.wires.BlockHVConnectorBase;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import com.magneticraft2.common.utils.IConnectorHV;
import com.magneticraft2.common.utils.generalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BlockEntityHVConnectorBase extends TileEntityMagneticraft2 {
    public BlockPos leftConnectionPos = null;
    public BlockPos rightConnectionPos = null;
    private boolean leftConnected;
    private boolean rightConnected;
    private BlockEntityHVConnectorBase master;
    private boolean startcon;
    private boolean isMaster;
    public BlockEntityHVConnectorBase(BlockPos pos, BlockState state) {
        super(FinalRegistry.Tile_HVConnector_Base.get(), pos, state);
    }


    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create( this );
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag( pkt.getTag() );
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag nbtTagCompound = new CompoundTag();
        saveAdditional(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(CompoundTag parentNBTTagCompound)
    {
        load(parentNBTTagCompound);
    }



    private void loadClientData(CompoundTag tag) {
        tag.putBoolean("rightCon", rightConnected);
        tag.putBoolean("leftCon", leftConnected);
        tag.putBoolean("isMaster", isMaster);
        if (rightConnectionPos != null) tag.putLong("rightP", rightConnectionPos.asLong());
        if (leftConnectionPos != null) tag.putLong("leftP", leftConnectionPos.asLong());
    }
    private void initializeNetworkIfNecessary(){
        if (master == null || master.isRemoved()){
            List<BlockEntityHVConnectorBase> connectedCables = new ArrayList<BlockEntityHVConnectorBase>();
            Stack<BlockEntityHVConnectorBase> traversingCables = new Stack<BlockEntityHVConnectorBase>();
            IConnectorHV inTransformerT = null;
            IConnectorHV outTransformerT = null;
            BlockEntityHVConnectorBase master = this;
            traversingCables.add(this);
            while (!traversingCables.isEmpty()){
                BlockEntityHVConnectorBase storage = traversingCables.pop();
                if (storage.isMaster()){
                    master = storage;
                    connectedCables.add(storage);
                    if (storage.isLeftConnected()){
                        BlockEntity te = level.getBlockEntity(storage.leftConnectionPos);
                        if (te instanceof BlockEntityHVConnectorBase && !connectedCables.contains(te)){
                            traversingCables.add((BlockEntityHVConnectorBase) te);
                        }
                        if (te instanceof IConnectorHV){
                            if (((IConnectorHV) te).isOutput()){
                                outTransformerT = (IConnectorHV) te;
                            }else{
                                inTransformerT = (IConnectorHV) te;
                            }
                        }
                    }
                    if (storage.isRightConnected()){
                        BlockEntity te = level.getBlockEntity(storage.rightConnectionPos);
                        if (te instanceof BlockEntityHVConnectorBase && !connectedCables.contains(te)){
                            traversingCables.add((BlockEntityHVConnectorBase) te);
                        }
                        if (te instanceof IConnectorHV){
                            ((IConnectorHV) te).setOtherSideTransformer(null);
                            if (((IConnectorHV) te).isOutput()){
                                outTransformerT = (IConnectorHV) te;
                            }else{
                                inTransformerT = (IConnectorHV) te;
                            }
                        }
                    }
                }

            }
            for (BlockEntityHVConnectorBase storage : connectedCables){
                storage.setMaster(master);
                storage.setChanged();
            }
            if (inTransformerT != null) inTransformerT.setOtherSideTransformer(outTransformerT);
            if (outTransformerT != null) outTransformerT.setOtherSideTransformer(inTransformerT);
            master.setChanged();
            setChanged();
        }
    }
    public void forceRecheck(){
        this.master = null;
        initializeNetworkIfNecessary();
    }
    public void removeCableAndSpawn(BlockPos connectedPos){
        disableConnectedCables(connectedPos);
        removeConnection(connectedPos);
        if (!level.isClientSide){
            generalUtils.spawnItemStack(level, worldPosition, new ItemStack(FinalRegistry.Item_Wire_Coil.get()));
        }
    }
    private void disableConnectedCables(BlockPos connectedPos){
        BlockEntity te = level.getBlockEntity(connectedPos);
        if (te instanceof BlockEntityHVConnectorBase){
            ((BlockEntityHVConnectorBase) te).removeConnection(getThisPosition());
        } else if (te instanceof IConnectorHV) {
            ((IConnectorHV)te).removeConnection();
        }
    }
    public void removeConnection(BlockPos pos){
        if (pos.equals(leftConnectionPos)){
            leftConnected = false;
            leftConnectionPos = null;
            master = null;
            initializeNetworkIfNecessary();
            this.sync();
        } else if (pos.equals(rightConnectionPos)) {
            rightConnected = false;
            rightConnectionPos = null;
            master = null;
            initializeNetworkIfNecessary();
            this.sync();
        }
    }
    public void removeAllConnections(){
        if (isRightConnected()) removeConnection(rightConnectionPos);
        if (isLeftConnected()) removeConnection(leftConnectionPos);
    }
    public boolean canConnect(){
        return !isLeftConnected() || !isRightConnected();
    }
    public void setConnection(BlockPos otherConnectorPos){
        boolean madeCon = false;
        if (!isLeftConnected()){
            LOGGER.info("Left");
            setLeftConnectionPos(otherConnectorPos);
            madeCon = true;
        } else if (!isRightConnected()) {
            LOGGER.info("Right");
            setRightConnectionPos(otherConnectorPos);
            madeCon = true;
        }
        if (madeCon){
            LOGGER.info("none");
            startcon = false;
            master = null;
            initializeNetworkIfNecessary();
        }
        this.sync();
    }
    public Direction getBlockFacing(){
        return level.getBlockState(getThisPosition()).getValue(BlockHVConnectorBase.FACING);
    }
    private void setLeftConnectionPos(BlockPos pos){
        leftConnectionPos = pos;
        leftConnected = true;
    }
    private void setRightConnectionPos(BlockPos pos) {
        rightConnectionPos = pos;
        rightConnected = true;
    }
    public boolean isMaster()
    {
        return isMaster;
    }
    public BlockEntityHVConnectorBase getMaster(){
        initializeNetworkIfNecessary();
        return master;
    }
    public void setMaster(BlockEntityHVConnectorBase master)
    {
        this.master = master;
        isMaster = master == this;
        setChanged();
    }
    public boolean isLeftConnected()
    {
        return leftConnected;
    }
    public boolean isRightConnected()
    {
        return rightConnected;
    }


    @Override
    public void onBlockBreak() {
        if (isLeftConnected()){
            removeCableAndSpawn(leftConnectionPos);
        }
        if (isRightConnected()){{
            removeCableAndSpawn(rightConnectionPos);
        }}
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (rightConnectionPos != null) compound.putLong("rightP", rightConnectionPos.asLong());
        if (leftConnectionPos != null) compound.putLong("leftP", leftConnectionPos.asLong());
        compound.putBoolean("rightCon", rightConnected);
        compound.putBoolean("leftCon", leftConnected);
        compound.putBoolean("isMaster", isMaster);
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        rightConnectionPos = BlockPos.of(compound.getLong("rightP"));
        leftConnectionPos = BlockPos.of(compound.getLong("leftP"));
        rightConnected = compound.getBoolean("rightCon");
        leftConnected = compound.getBoolean("leftCon");
        isMaster = compound.getBoolean("isMaster");
        super.load(compound);
    }


    @Override
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
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
        return level;
    }

    @Override
    public BlockPos getThisPosition() {
        return worldPosition;
    }

    @Override
    public CompoundTag sync() {
        LOGGER.info("Init sync");
        level.sendBlockUpdated( worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL );
        CompoundTag tag = super.getUpdateTag();
        loadClientData(tag);
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
