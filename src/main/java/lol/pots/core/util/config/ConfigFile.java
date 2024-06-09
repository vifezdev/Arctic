package lol.pots.core.util.config;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Collection;
import java.util.List;

public class ConfigFile {

    private final String configName;
    protected File file;
    protected YamlConfiguration config;

    @SneakyThrows
    public ConfigFile(String configName) {
        this.configName = configName;

        File serverDir = Bukkit.getServer().getWorldContainer();
        File pluginsDir = new File(serverDir, "plugins");
        String pluginName = "Arctic";  // Get plugin's name
        File pluginDir = new File(pluginsDir, pluginName);

        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
        }

        file = new File(pluginDir, configName);

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "There was an error initializing the config file: " + configName);
        }
    }

    public void init() throws IOException {
        if (file == null) {
            throw new IOException("File object is null. Cannot initialize.");
        }

        // Update the config file before loading it
        updateConfig();

        config = YamlConfiguration.loadConfiguration(file);
    }

    @SneakyThrows
    public void updateConfig() {
        // Ensure default config is saved first
        saveDefaultConfig();

        File configFile = new File(file.getParent(), configName);

        // Load default (or updated) configuration
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(getClass().getResourceAsStream("/" + configName)));

        // Load existing configuration
        YamlConfiguration existingConfig = YamlConfiguration.loadConfiguration(configFile);

        // Merge new settings from the defaultConfig into existingConfig
        for (String key : defaultConfig.getKeys(true)) {
            if (!existingConfig.contains(key)) {
                existingConfig.set(key, defaultConfig.get(key));
            }
        }

        // Save the merged configuration
        existingConfig.save(configFile);
    }


    private void saveDefaultConfig() {
        // Implement the logic to save default config if it doesn't exist.
    }

    public void loadDefaults() throws IOException {
        InputStream is = getClass().getResourceAsStream("/" + configName);
        if (is != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(readFile(is));
            writer.close();
        }
    }

    public String readFile(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String content = "";
        String line;
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }

        reader.close();
        return content.trim();
    }

    public void save() {
        try {
            getConfig().save(file);
            System.out.println("Config file saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public Collection<String> getSection(String section) {
        return this.config.getConfigurationSection(section).getKeys(false);
    }

    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }


    public YamlConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        try {
            if (!file.exists()) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Config file " + configName + " does not exist. Creating...");
                file.createNewFile();
                loadDefaults();
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Reloading config file " + configName);
            }

            if (config == null) {
                config = new YamlConfiguration();
            }

            config.load(file);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully reloaded the config file: " + configName);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "There was an error reloading the file " + configName);
        }
    }

    public ConfigFile get() {
        return this;
    }

    public void set(String path, Object value) {
        this.config.set(path, value);
        save();
    }
}
