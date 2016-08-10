package com.pvpMinecraft.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class BaseYamlSettingsFile {

	private final String _filename;
	protected final Plugin plugin;
	private final Logger _logger;
	private final File _configLocation;
	private YamlConfiguration configFile;
	
	public BaseYamlSettingsFile(Plugin plugin, String fileName) {
		this._filename = fileName;
		this.plugin = plugin;
		this._logger = plugin.getLogger();
		this._configLocation = plugin.getDataFolder();
	}
	
	protected void log(Level level, String message) {
		_logger.log(level, message);
	}
	
	public boolean load() {
		try {
	        File cFile = new File(_configLocation, _filename);
	        if (!cFile.exists()) {
				_configLocation.mkdirs();
	            copyFile(plugin.getResource(_filename), cFile);
	            log(Level.CONFIG, "Configuration file " + _filename + " created.");
	        }
	        configFile = YamlConfiguration.loadConfiguration(cFile);
	        log(Level.CONFIG, "Configuration file " + _filename + " loaded.");
	        return true;
		}
		catch(IOException e) {
	        log(Level.CONFIG, "Failed to create configuration file: " + _filename);
	        e.printStackTrace();
	        return false;
		}
	}

    private void copyFile(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
    }

	private YamlConfiguration getConfig(String name, Object def) {
		if (this.configFile == null) {
			log(Level.SEVERE, "The configuration file " + _filename + " not been loaded.");
			return new YamlConfiguration();
		}
		if (!this.configFile.contains(name)) {
			log(Level.WARNING, "The configuration does not contain property '" + name + "'.");
			this.configFile.set(name, def);
			try {
				this.configFile.save(new File(_configLocation, _filename));
			}
			catch (IOException e) {
	        	log(Level.SEVERE, e.toString());
	        	e.printStackTrace();
			}
		}
		return this.configFile;
	}
	
	public String translate(String text) {
		return ChatColor.translateAlternateColorCodes('&', text)
        		.replace("&\\", "\n") // < newline escape
        		.replace("&.", "&");  // < & escape
	}
	
	public String get(String name, String def) {
		return translate(getConfig(name, def).getString(name, def));
	}

	public String[] getArray(String name, String[] def) {
		String[] result = def.clone();
		List<String> list = getConfig(name, def).getStringList(name);
		if (list != null && !list.isEmpty()) {
			result = list.toArray(new String[list.size()]);
		}
		
		for (int ix = 0; ix < result.length; ix++) {
			result[ix] = translate(result[ix]);
		}
		return result;
	}

	public String format(String name, String def, Object... args) {
		String tmp = String.format(get(name, def));
		
		log(Level.FINE, "Found fields: " + String.valueOf(args.getClass().getFields().length));
		for(int ix = 0; ix < (args.length - 1); ix++) {
			tmp = tmp.replace("{" + String.valueOf(args[ix]) + "}", String.valueOf(args[ix + 1]));
		}
		return tmp;
	}

	public boolean getBoolean(String name, boolean def) {
		return getConfig(name, def).getBoolean(name, def);
	}
	
	public int getInt(String name, int def) {
		return getConfig(name, def).getInt(name, def);
	}
}
