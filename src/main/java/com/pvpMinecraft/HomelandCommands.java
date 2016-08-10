package com.pvpMinecraft;

import com.pvpMinecraft.commands.*;

public class HomelandCommands extends com.pvpMinecraft.utils.BaseYamlSettingsFile {
	public HomelandCommands(HomelandSecurityPlugin plugin) { super(plugin, "plugin.yml"); }
	
	public void RegisterAll() {
		HomelandSecurityPlugin plugin = (HomelandSecurityPlugin)this.plugin;
		new CmdClaim(plugin);
	}
}
