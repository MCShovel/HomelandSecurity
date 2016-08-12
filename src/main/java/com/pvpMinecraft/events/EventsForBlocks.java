package com.pvpMinecraft.events;

import java.util.Set;
import java.util.logging.Level;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.pvpMinecraft.HomelandSecurityPlugin;
import com.pvpMinecraft.utils.ProgressBarDisplay;

public class EventsForBlocks implements Listener {
	private final HomelandSecurityPlugin plugin;
	private ProgressBarDisplay _prog;
	private float amt;

	public EventsForBlocks(HomelandSecurityPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		plugin.log(Level.INFO, "onBlockBreak");
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockDamaged(BlockDamageEvent event) {
    	plugin.log(Level.INFO, "onBlockDamaged");
    	event.setCancelled(true);
		event.getPlayer().setGameMode(GameMode.ADVENTURE);
		if (_prog != null) {
			_prog.Hide();
		}
		_prog = new ProgressBarDisplay(event.getPlayer(), "Breaking block...");
		_prog.Progress(amt = 0);
    }

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
    	plugin.log(Level.INFO, "onPlayerInteract");
		if (_prog != null) {
			_prog.Progress(amt += 0.1);
			if (amt >= 1) {
				_prog.Hide();
				_prog = null;
			}
		}
	}
}
