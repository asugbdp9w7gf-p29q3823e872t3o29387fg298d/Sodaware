package me.soda.sodaware.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.soda.sodaware.client.util.WurstplusOnlineFriends;
import net.minecraft.entity.Entity;

public class WurstplusFriendList extends WurstplusPinnable {
    
    public WurstplusFriendList() {
        super("Friends", "Friends", 1, 0, 0);
    }

    int passes;

    public static ChatFormatting bold = ChatFormatting.BOLD;

    @Override
	public void render() {
		int nl_r = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Sodaware.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

        String line1 = bold + "the_fellas: ";
        
        passes = 0;

        create_line(line1, this.docking(1, line1), 2, nl_r, nl_g, nl_b, nl_a);
        
        if (!WurstplusOnlineFriends.getFriends().isEmpty()) {
            for (Entity e : WurstplusOnlineFriends.getFriends()) {
                passes++;
                create_line(e.getName(), this.docking(1, e.getName()), this.get(line1, "height")*passes, nl_r, nl_g, nl_b, nl_a);
            }
        }

		this.set_width(this.get(line1, "width") + 2);
		this.set_height(this.get(line1, "height") + 2);
	}

}