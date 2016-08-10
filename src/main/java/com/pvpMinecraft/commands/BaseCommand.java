package com.pvpMinecraft.commands;

import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import com.pvpMinecraft.HomelandSecurityPlugin;

public abstract class BaseCommand implements CommandExecutor {

    protected final HomelandSecurityPlugin plugin;
    protected final String permission;
    protected final String cmdName;
    private final int minArgs;

    public BaseCommand(HomelandSecurityPlugin plugin, String permission, String command, String[] alias, String desc, int minArg) {
        this.plugin = plugin;
        this.permission = "homeland." + permission;
        this.cmdName = command;
        this.minArgs = minArg;

        PluginCommand cmd = this.plugin.getCommand(this.cmdName);
        if (cmd == null) {
        	plugin.log(Level.SEVERE, "Unable to locate command: " + command);
        	return;
        }

        String usage = plugin.Messages.format("cmd-usage", "Usage: /{name} {args}", "name", cmdName, "args", minArgs > 0 ? "..." : "");
        plugin.Messages.get("commands." + command + ".description", desc);
        plugin.Messages.getArray("commands." + command + ".aliases", alias);
        plugin.Messages.get("commands." + command + ".usage", usage);
        plugin.Messages.get("commands." + command + ".permission", this.permission);
        plugin.Messages.get("commands." + command + ".permission-message", plugin.Messages.PermissionDenied());
        
        cmd.setExecutor(this);
    }
    
    protected abstract boolean onPlayerCommand(Player player, Command cmd, String commandLabel, String[] args);

    protected boolean onConsoleCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	return false;
    }

    public final boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	
        if (cmd == null || !cmdName.equalsIgnoreCase(cmd.getName()) || args == null || args.length < minArgs) {
            return false;
        }

		if (!sender.hasPermission(permission)) {
			sender.sendMessage(plugin.Messages.PermissionDenied());
			plugin.log(Level.SEVERE, "The user " + sender.getName() + " needs permission " + permission + " to access to the command " + cmdName);
			return true;
		}

        if (sender instanceof Player) {
        	Player player = (Player) sender;
        	return onPlayerCommand(player, cmd, commandLabel, args);
        }
        else {
        	return onConsoleCommand(sender, cmd, commandLabel, args);
        }
    }
}
