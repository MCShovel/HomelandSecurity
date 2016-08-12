package com.pvpMinecraft;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class HomelandSecurityPlugin extends JavaPlugin {

	private final Logger _logger;
	public final boolean _loaded;
	public final HomelandConfig Config;
	public final HomelandMessage Messages;
	private final HomelandCommands Commands;
	private final HomelandEvents Events;
	
	public HomelandSecurityPlugin() {
		_logger = getLogger();
		log(Level.FINE, "Plugin initializing...");

		Config = new HomelandConfig(this);
		Messages = new HomelandMessage(this);
		Commands = new HomelandCommands(this);
		Events = new HomelandEvents(this);
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

    	Commands.RegisterAll();
    	Events.RegisterAll();
    	log(Level.CONFIG, "Plugin ready.");
    }

    @Override
    public void onDisable() {
    	Events.UnregisterAll();
		log(Level.CONFIG, "Plugin unregistered for events.");
    }

}
