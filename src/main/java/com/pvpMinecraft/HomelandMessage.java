package com.pvpMinecraft;

public class HomelandMessage extends com.pvpMinecraft.utils.BaseYamlSettingsFile {
	public HomelandMessage(HomelandSecurityPlugin plugin) { super(plugin, "message.yml"); }
	
	public String PermissionDenied() { return get("no-access", "&4You do not have permission to this command."); }
}
