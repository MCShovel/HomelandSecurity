package com.pvpMinecraft.commands;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.pvpMinecraft.display.*;
import com.pvpMinecraft.utils.ProgressBarDisplay;

public class CmdClaim extends BaseCommand {

	public CmdClaim(com.pvpMinecraft.HomelandSecurityPlugin plugin) {
		super(plugin, "player", "claim", new String[0], "Creates a claim around you.", 0);
	}

	@Override
	protected boolean onPlayerCommand(final Player player, Command cmd, String commandLabel, String[] args) {
		
		BRect where = new BRect(player.getLocation()).Expand(5);
		plugin.log(Level.INFO, "Creating claim: " + where);
		VisualizationBox box = VisualizationBox.FromLocation(player.getWorld(), where, VisualizationType.Claim, player.getLocation());
		box.Draw(player, true);

		final ProgressBarDisplay bar = new ProgressBarDisplay(player, "Hello World", 0);
		
		final int[] ids = new int[1];
		
        ids[0] = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int counter = 0;
			@Override
			public void run() {
				plugin.log(Level.INFO, "TICK: " + counter);
				try {
					if (counter++ > 30) {
						bar.Hide();
						plugin.getServer().getScheduler().cancelTask(ids[0]);
					} else {
						bar.Progress((1.0 / 30) * counter);
					}
				}
				catch(Exception e) {
					plugin.log(Level.SEVERE, e.toString());
				}
			}
		}, 20, 20);

		return true;
	}
}
