package me.soda.sodaware.client.modules.chat;


import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.client.event.events.WurstplusEventPacket;
import me.soda.sodaware.client.guiscreen.settings.WurstplusSetting;
import me.soda.sodaware.client.modules.WurstplusCategory;
import me.soda.sodaware.client.modules.WurstplusHack;
import me.soda.sodaware.client.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;

import java.text.SimpleDateFormat;
import java.util.Date;

//mhm chatmods

public final class WurstplusChatMods extends WurstplusHack {
    
    public WurstplusChatMods() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Chat Modifications";
        this.tag = "ChatModifications";
        this.description = "this breaks things";
    }

    // GuiNewChat nc = new GuiNewChat(mc);

    WurstplusSetting timestamps = create("Timestamps", "ChatModsTimeStamps", true);
    WurstplusSetting dateformat = create("Date Format", "ChatModsDateFormat", "24HR", combobox("24HR", "12HR"));
    WurstplusSetting name_highlight = create("Name Highlight", "ChatModsNameHighlight", true);

    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> PacketEvent = new Listener<>(event -> {

        if (event.get_packet() instanceof SPacketChat) {

            final SPacketChat packet = (SPacketChat) event.get_packet();

            if (packet.getChatComponent() instanceof TextComponentString) {
                final TextComponentString component = (TextComponentString) packet.getChatComponent();

              if (timestamps.get_value(true)) {

                    String date = "";

                    if (dateformat.in("12HR")) {
                        date = new SimpleDateFormat("h:mm a").format(new Date());
                    }

                    if (dateformat.in("24HR")) {
                        date = new SimpleDateFormat("k:mm").format(new Date());

                    }

                    component.text = "\2477[" + date + "]\247r " + component.text;

                }

                String text = component.getFormattedText();

                if (text.contains("combat for")) return;

                if (name_highlight.get_value(true) && mc.player != null) {

                    if (text.toLowerCase().contains(mc.player.getName().toLowerCase())) {

                        text = text.replaceAll("(?i)" + mc.player.getName(), ChatFormatting.GOLD + mc.player.getName() + ChatFormatting.RESET);

                    }

                }

                event.cancel();

                WurstplusMessageUtil.client_message(text);

            }
        }
    });

}
