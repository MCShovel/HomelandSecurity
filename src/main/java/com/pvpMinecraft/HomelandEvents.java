package com.pvpMinecraft;

import java.util.ArrayList;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.pvpMinecraft.events.*;

public class HomelandEvents {
	final HomelandSecurityPlugin plugin;	
	final ArrayList<Listener> listeners = new ArrayList<Listener>();
	
	public HomelandEvents(HomelandSecurityPlugin plugin) {
		this.plugin = plugin;
	}
	
	private void RegisterOne(Listener l) {
		plugin.getServer().getPluginManager().registerEvents(l, plugin);
		listeners.add(l);
	}
	
	public void RegisterAll() {
		RegisterOne(new EventsForBlocks(plugin));
	}

	public void UnregisterAll() {
		for (int ix = listeners.size() - 1; ix >= 0; ix--) {
	    	HandlerList.unregisterAll(listeners.remove(ix));
		}
	}
}
