package com.pvpMinecraft;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class HomelandSecurityPlugin extends JavaPlugin {

	private final Logger _logger;
	public final boolean _loaded;
	public final HomelandConfig Config;
	public final HomelandMessage Messages;
	private final HomelandCommands Commands;
	
	public HomelandSecurityPlugin() {
		_logger = getLogger();
		log(Level.FINE, "Plugin initializing...");

		Config = new HomelandConfig(this);
		Messages = new HomelandMessage(this);
		Commands = new HomelandCommands(this);
		_loaded = Config.load() && Messages.load() && Commands.load();
	}

	public void log(Level level, String text) {
		_logger.log(level, text);
	}

    @Override
    public void onEnable() {
    	if (!_loaded) {
    		log(Level.SEVERE, "Configuration did not load, disabling service.");
    		return;
    	}
        //getServer().getPluginManager().registerEvents(_listener, this);
    	Commands.RegisterAll();
    	log(Level.CONFIG, "Plugin ready.");
    }

    @Override
    public void onDisable() {
    	//HandlerList.unregisterAll(_listener);
		log(Level.CONFIG, "Plugin unregistered for events.");
    }

}
