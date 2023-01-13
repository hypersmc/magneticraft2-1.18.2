package com.magneticraft2.common.item.wire;

import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import com.magneticraft2.common.tile.wire.BlockEntityTransformerHV;
import com.magneticraft2.common.utils.IConnectorHV;
import com.magneticraft2.common.utils.Magneticraft2ConfigCommon;
import com.magneticraft2.common.utils.generalUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.social.PlayerEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class itemWireCoil extends Item {
    private BlockPos firstConnectionPos;
    private boolean isSecond = false;
    public static final Logger LOGGER = LogManager.getLogger("ItemMGC2Core");
    public itemWireCoil() {
        super(new Properties().stacksTo(64).setNoRepair().tab(FinalRegistry.MC2Items));
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) InteractionResultHolder.pass(pContext.getPlayer());
        else {
            if (magneticraft2.devmode) {
                if (pContext.getPlayer().isCrouching()) {
                    BlockEntity te = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
                    if (te instanceof BlockEntityHVConnectorBase) {
                        generalUtils.sendChatMessage(pContext.getPlayer(), "" + ((BlockEntityHVConnectorBase) te).isRightConnected());
                        generalUtils.sendChatMessage(pContext.getPlayer(), "" + ((BlockEntityHVConnectorBase) te).isLeftConnected());
                    }
                }
            }
            if (pContext.getHand().equals(InteractionHand.MAIN_HAND)){
                BlockEntity te = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
                if (te == null){
                    cleanConnection(pContext.getPlayer());
                }
                ItemStack itemStack = pContext.getItemInHand();
                if (te instanceof IConnectorHV)
                {
                    IConnectorHV teT = (IConnectorHV) te;
                    if (teT instanceof BlockEntityTransformerHV) teT = ((BlockEntityTransformerHV) teT).getMaster();
                    if (!isSecond)
                    {
                        if (teT.canConnect(pContext.getClickedPos()))
                        {
                            firstConnectionPos = teT.getConnectorPos();
                            isSecond = true;
                            pContext.getPlayer().sendMessage(new TranslatableComponent("message.magneticraft2.connection_start"), Util.NIL_UUID);
                            pContext.getPlayer().displayClientMessage(new TranslatableComponent("message.magneticraft2.connection_start_coords", "§5" + pContext.getClickedPos().getX(), "§5" + pContext.getClickedPos().getY(), "§5" + pContext.getClickedPos().getZ()), true);

                        }
                        else
                        {
                            pContext.getPlayer().sendMessage(new TranslatableComponent("message.magneticraft2.connection_in_use"), Util.NIL_UUID);
                        }
                    }
                    else
                    {
                        int distance = generalUtils.getDistancePointToPoint(firstConnectionPos, pContext.getClickedPos());
                        if (teT.getConnectorPos() != firstConnectionPos && teT.canConnect(pContext.getClickedPos()) && distance > 0 && distance <= Magneticraft2ConfigCommon.GENERAL.MaxWireLenght.get())
                        {
                            isSecond = false;
                            connectFirst(pContext.getLevel(), teT.getConnectorPos());
                            teT.connect(firstConnectionPos);
                            pContext.getPlayer().sendMessage(new TranslatableComponent("message.magneticraft2.connecteddistance",  distance), Util.NIL_UUID);
                            itemStack.shrink(1);
                        }
                        else
                        {
                            if (distance > Magneticraft2ConfigCommon.GENERAL.MaxWireLenght.get())
                                pContext.getPlayer().sendMessage(new TranslatableComponent("message.magneticraf2.toofar", distance), Util.NIL_UUID);
                            cleanConnection(pContext.getPlayer());
                        }
                    }
                }
                else if (te instanceof BlockEntityHVConnectorBase)
                {
                    BlockEntityHVConnectorBase teT = (BlockEntityHVConnectorBase) te;
                    if (!isSecond)
                    {
                        if (teT.canConnect())
                        {
                            firstConnectionPos = teT.getBlockPos();
                            isSecond = true;
                            pContext.getPlayer().sendMessage(new TranslatableComponent("message.magneticraft2.connection_start"), Util.NIL_UUID);
                            pContext.getPlayer().displayClientMessage(new TranslatableComponent( "message.magneticraft2.connection_start_coords", "§5" + pContext.getClickedPos().getX(), "§5" + pContext.getClickedPos().getY(), "§5" + pContext.getClickedPos().getZ()), true);

                        }
                        else
                        {
                            pContext.getPlayer().sendMessage( new TranslatableComponent("message.magneticraft2.connection_in_use"), Util.NIL_UUID);
                        }
                    }
                    else
                    {
                        int distance = generalUtils.getDistancePointToPoint(firstConnectionPos, pContext.getClickedPos());
                        if (teT.getBlockPos() != firstConnectionPos && teT.canConnect() && distance > 0 && distance <= Magneticraft2ConfigCommon.GENERAL.MaxWireLenght.get())
                        {
                            isSecond = false;
                            connectFirst(pContext.getLevel(), teT.getBlockPos());
                            teT.setConnection(firstConnectionPos);
                            pContext.getPlayer().sendMessage(new TranslatableComponent("message.magneticraft2.connecteddistance", distance), Util.NIL_UUID);
                            ((BlockEntityHVConnectorBase) te).sync();
                            itemStack.shrink(1);
                        }
                        else
                        {
                            if (distance > Magneticraft2ConfigCommon.GENERAL.MaxWireLenght.get())
                                pContext.getPlayer().sendMessage(new TranslatableComponent("message.magneticraf2.toofar", distance), Util.NIL_UUID);
                            cleanConnection(pContext.getPlayer());}
                    }
                }
                else if (isSecond)
                {
                    cleanConnection(pContext.getPlayer());
                }
            }
        }
        return super.useOn(pContext);

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        return super.use(pLevel, pPlayer, pUsedHand);
    }
    public String getDistanceText(Player player){

        if (!isSecond) return "";
        int distance = generalUtils.getDistancePointToPoint(firstConnectionPos, player.getOnPos());
        String text = new TranslatableComponent("message.magneticraft2.distance", distance).toString();
        return (distance > Magneticraft2ConfigCommon.GENERAL.MaxWireLenght.get() ? ChatFormatting.RED : ChatFormatting.GREEN) + text;
    }
    private void cleanConnection(Player player){
        isSecond = false;
        player.sendMessage(new TranslatableComponent("message.magneticraft2.cannot_connect"), Util.NIL_UUID);

    }
    
    private void connectFirst(Level world, BlockPos endPos)
    {
        BlockEntity te = world.getBlockEntity(firstConnectionPos);
        if (te instanceof IConnectorHV)
        {
            ((IConnectorHV) te).connect(endPos);
        }
        else if (te instanceof BlockEntityHVConnectorBase)
        {
            ((BlockEntityHVConnectorBase) te).setConnection(endPos);
        }
    }
}
