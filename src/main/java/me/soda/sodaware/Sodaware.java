package me.soda.sodaware;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.soda.sodaware.client.auth.FrameUtil;
import me.soda.sodaware.client.auth.HWIDUtil;
import me.soda.sodaware.client.auth.NetworkUtil;
import me.soda.sodaware.client.auth.NoStackTraceThrowable;
import me.soda.turok.Turok;
import me.soda.turok.task.Font;
import me.soda.sodaware.client.event.WurstplusEventHandler;
import me.soda.sodaware.client.event.WurstplusEventRegister;
import me.soda.sodaware.client.guiscreen.WurstplusGUI;
import me.soda.sodaware.client.guiscreen.WurstplusHUD;
import me.soda.sodaware.client.manager.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = "sodaware", version = Sodaware.WURSTPLUS_VERSION)
public class Sodaware {

	@Mod.Instance
	private static Sodaware MASTER;

	public static final String WURSTPLUS_NAME = "sodaware";
	public static final String WURSTPLUS_VERSION = "1.1";
	public static final String WURSTPLUS_SIGN = " ";

	public static final int WURSTPLUS_KEY_GUI = Keyboard.KEY_RSHIFT;
	public static final int WURSTPLUS_KEY_DELETE = Keyboard.KEY_DELETE;
	public static final int WURSTPLUS_KEY_GUI_ESCAPE = Keyboard.KEY_ESCAPE;

	public static Logger wurstplus_register_log;

	private static WurstplusSettingManager setting_manager;
	private static WurstplusConfigManager config_manager;
	private static WurstplusModuleManager module_manager;
	private static WurstplusHUDManager hud_manager;

	public static WurstplusGUI click_gui;
	public static WurstplusHUD click_hud;

	public static Turok turok;

	public static ChatFormatting g = ChatFormatting.DARK_GRAY;
	public static ChatFormatting r = ChatFormatting.RESET;

	public static List<String> hwidList = new ArrayList<>();
	public static final String KEY = "verify";
	public static final String HWID_URL = "https://pastebin.com/raw/sseEEBxP";

	@Mod.EventHandler
	public void WurstplusStarting(FMLInitializationEvent event) {
		Verify();


		init_log(WURSTPLUS_NAME);

		WurstplusEventHandler.INSTANCE = new WurstplusEventHandler();

		send_minecraft_log("initialising managers");

		setting_manager = new WurstplusSettingManager();
		config_manager = new WurstplusConfigManager();
		module_manager = new WurstplusModuleManager();
		hud_manager = new WurstplusHUDManager();

		WurstplusEventManager event_manager = new WurstplusEventManager();
		WurstplusCommandManager command_manager = new WurstplusCommandManager(); // hack

		send_minecraft_log("done");

		send_minecraft_log("initialising guis");

		Display.setTitle("sodaware");
		click_gui = new WurstplusGUI();
		click_hud = new WurstplusHUD();

		send_minecraft_log("done");

		send_minecraft_log("initialising skidded framework");

		turok = new Turok("Turok");

		send_minecraft_log("done");

		send_minecraft_log("initialising commands and events");

		// Register event modules and manager.
		WurstplusEventRegister.register_command_manager(command_manager);
		WurstplusEventRegister.register_module_manager(event_manager);

		send_minecraft_log("done");

		send_minecraft_log("loading settings");

		config_manager.load_settings();

		send_minecraft_log("done");

		if (module_manager.get_module_with_tag("GUI").is_active()) {
			module_manager.get_module_with_tag("GUI").set_active(false);
		}

		if (module_manager.get_module_with_tag("HUD").is_active()) {
			module_manager.get_module_with_tag("HUD").set_active(false);
		}

		if (module_manager.get_module_with_tag("RichPresence").is_active()) {
			module_manager.get_module_with_tag("RichPresence").set_active(false);
		} //don't turn ON on starting cuz that crashes

		send_minecraft_log("client started");
		send_minecraft_log("boom");

	}

	public void Verify() {
		//Here we get the HWID List From URL
		hwidList = NetworkUtil.getHWIDList();

		//Check HWID
		if(!hwidList.contains(HWIDUtil.getEncryptedHWID(KEY))){
			//Shutdown client and display message
			FrameUtil.Display();
			throw new NoStackTraceThrowable("Verify HWID Failed!");
		}

	}

	public void init_log(String name) {
		wurstplus_register_log = LogManager.getLogger(name);

		send_minecraft_log("starting sodaware");
	}

	public static void send_minecraft_log(String log) {
		wurstplus_register_log.info(log);
	}

	public static String get_name() {
		return  WURSTPLUS_NAME;
	}

	public static String get_version() {
		return WURSTPLUS_VERSION;
	}

	public static String get_actual_user() {
		return Minecraft.getMinecraft().getSession().getUsername();
	}

	public static WurstplusConfigManager get_config_manager() {
		return config_manager;
	}

	public static WurstplusModuleManager get_hack_manager() {
		return module_manager;
	}

	public static WurstplusSettingManager get_setting_manager() {
		return setting_manager;
	}

	public static WurstplusHUDManager get_hud_manager() {
		return hud_manager;
	}

	public static WurstplusModuleManager get_module_manager() { return module_manager; }

	public static WurstplusEventHandler get_event_handler() {
		return WurstplusEventHandler.INSTANCE;
	}

	public static String smoth(String base) {
		return Font.smoth(base);
	}
}