package com.mrcrayfish.backpacked.client;

import com.mrcrayfish.backpacked.Backpacked;
import com.mrcrayfish.backpacked.client.gui.screen.inventory.BackpackScreen;
import com.mrcrayfish.backpacked.network.PacketHandler;
import com.mrcrayfish.backpacked.network.message.MessageOpenBackpack;
import com.mrcrayfish.backpacked.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

/**
 * Author: MrCrayfish
 */
public class ClientEvents
{
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        if(minecraft.currentScreen instanceof BackpackScreen)
        {
            if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == ClientProxy.KEY_BACKPACK.getKey().getKeyCode())
            {
                minecraft.player.closeScreen();
            }
        }
        else if(minecraft.player != null && minecraft.currentScreen == null)
        {
            ClientPlayerEntity player = minecraft.player;
            if(ClientProxy.KEY_BACKPACK.isKeyDown() && ClientProxy.KEY_BACKPACK.isPressed())
            {
                if(!Backpacked.getBackpackStack(player).isEmpty())
                {
                    PacketHandler.instance.sendToServer(new MessageOpenBackpack());
                    minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.75F, 1.0F));
                }
            }
        }
    }
}
