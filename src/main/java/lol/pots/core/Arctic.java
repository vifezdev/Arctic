package lol.pots.core;

import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import lol.pots.core.command.CommandManager;
import lol.pots.core.filter.FilterManagement;
import lol.pots.core.grant.packet.RollingKey;
import lol.pots.core.handler.*;
import lol.pots.core.handler.LadderHandler;
import lol.pots.core.listener.*;
import lol.pots.core.mongo.MongoHandler;
import lol.pots.core.profile.Profile;
import lol.pots.core.redis.RedisHandler;
import lol.pots.core.task.FrozenTask;
import lol.pots.core.util.CC;
import lol.pots.core.util.SystemInfoLongKeyGenerator;
import lol.pots.core.util.command.CommandService;
import lol.pots.core.util.command.Drink;
import lol.pots.core.util.config.ConfigFile;
import lol.pots.core.util.menu.MenuListener;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Arctic extends JavaPlugin {
    long timeTracker;


    public Arctic(final JavaPluginLoader loader, final PluginDescriptionFile description, final File dataFolder, final File file) {
        super(loader, description, dataFolder, file);
    }

    @Getter
    public static Arctic instance;

    private MongoHandler mongoHandler;
    private RedisHandler redisHandler;
    private ProfileHandler profileHandler;
    private RankHandler rankHandler;
    private TagHandler tagHandler;
    private VanishHandler vanishHandler;
    private LadderHandler ladderHandler;
    private FilterManagement filterManagement;
    private ConversationHandler conversationHandler;
    private CommandService drink;
    private JsonParser parser = new JsonParser();


    @Getter
    @Setter
    private RollingKey rollingKey;

    @Getter
    @Setter
    private ConfigFile rankConfig, msgConfig, tagConfig, settingsConfig, filterConfig, ladderConfig;

    public Arctic() throws MalformedURLException {
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
    }


    @Override
    public void onLoad() {
        timeTracker = System.currentTimeMillis();
    }

    @Override
    public void onEnable() {

        instance = this;

        initConfigs();
        setupMongoRedisHandler();
        try {
            registerHandlers();
        } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        registerListeners();
        registerCommands();
        initTask();
        sendTitle();

    }

    private void initConfigs() {
        rankConfig = new ConfigFile("ranks.yml");
        tagConfig = new ConfigFile("tags.yml");
        msgConfig = new ConfigFile("messages.yml");
        filterConfig = new ConfigFile("filter.yml");
        settingsConfig = new ConfigFile("settings.yml");
        ladderConfig = new ConfigFile("ladder.yml");
    }

    public void reloadConfigs() {
        rankConfig.reloadConfig();
        msgConfig.reloadConfig();
    }

    private void setupMongoRedisHandler() {
        String host;
        int port;
        boolean uri = getSettingsConfig().getBoolean("MONGO.URI.ENABLED");
        String connectionString = getSettingsConfig().getString("MONGO.URI.CONNECTION-STRING");
        host = getSettingsConfig().getString("MONGO.DEFAULT.HOST");
        port = getSettingsConfig().getInt("MONGO.DEFAULT.PORT");
        String database = getSettingsConfig().getString("MONGO.DEFAULT.DATABASE");
        boolean authentication = getSettingsConfig().getBoolean("MONGO.DEFAULT.AUTHENTICATION.ENABLED");
        String username = getSettingsConfig().getString("MONGO.DEFAULT.AUTHENTICATION.USERNAME");
        String password = getSettingsConfig().getString("MONGO.DEFAULT.AUTHENTICATION.PASSWORD");

        mongoHandler = new MongoHandler(uri, connectionString, host, port, database, authentication, username, password);

        host = getSettingsConfig().getString("REDIS.HOST");
        port = getSettingsConfig().getInt("REDIS.PORT");
        password = getSettingsConfig().getString("REDIS.PASSWORD");
        String channel = getSettingsConfig().getString("REDIS.CHANNEL");

        redisHandler = new RedisHandler(host, port, channel, password);
        redisHandler.connect();
    }

    private void registerHandlers() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        profileHandler = new ProfileHandler();
        rankHandler = new RankHandler();
        rankHandler.load();
        tagHandler = new TagHandler();
        ladderHandler = new LadderHandler();
        tagHandler.init();
        vanishHandler = new VanishHandler();
        filterManagement = new FilterManagement();
        conversationHandler = new ConversationHandler();
    }

    private void initTask() {
        new FrozenTask(this).startRepeatingTask();
    }

    private void registerCommands() {
        drink = Drink.get(this);

        CommandManager commandsManager = new CommandManager(this, drink);
        commandsManager.init();
    }

    private void registerListeners() {


        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new GrantAddListener(), this);
        Bukkit.getPluginManager().registerEvents(new GrantPardonListener(), this);
        Bukkit.getPluginManager().registerEvents(new FreezeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PunishmentPardonListener(), this);
        long startTime = System.currentTimeMillis();
        rollingKey = new RollingKey(4001, SystemInfoLongKeyGenerator.generateUniqueLongKey());
    }

    @Override
    public void onDisable() {
        List<Profile> profilesToRemove = new ArrayList<>();
        for (Profile profile : profileHandler.getProfiles()) {
            profile.setLastSeenOn(System.currentTimeMillis());
            profile.setLastSeenServer("None");
            profile.save();
            conversationHandler.removeConversation(profile.getUuid());
            profilesToRemove.add(profile);
        }
        for (Profile profile : profilesToRemove) {
            profileHandler.getProfiles().remove(profile);
        }
        profilesToRemove.clear();
        instance = null;
    }

    public void broadcastMessage(String message, String permission) {
        for (Player player : getServer().getOnlinePlayers()) {
            if (player.hasPermission(permission)) player.sendMessage(CC.translate(message));
        }
    }

    public void broadcastMessage(String message, String permission, Player exception) {
        for (Player player : getServer().getOnlinePlayers()) {
            if(player.getUniqueId().equals(exception.getUniqueId())) return;
            if (player.hasPermission(permission)) player.sendMessage(CC.translate(message));
        }
    }

    public void broadcastMessage(String message) {
        for (Player player : getServer().getOnlinePlayers()) {
            player.sendMessage(CC.translate(message));
        }
    }

    public ConfigFile getConfigByName(String name){
        switch (name){
            case "settings":
                return getSettingsConfig();
            case "tag":
                return getTagConfig();
            case "ranks":
                return getRankConfig();
            case "ladder":
                return getLadderConfig();
            case "messages":
                return getMsgConfig();
        }
        return null;
    }


    public void sendTitle(){

        sendMsg("&7&m-----------------------------------------------------");
        sendMsg(" ");
        sendMsg(translateMsg("&7* &fSucessuflly loaded &bAcritc &7[&b",getDescription().getVersion() + "&7]"));
        sendMsg(" ");
        sendMsg("&b           .--.");
        sendMsg("&b          |o_o |");
        sendMsg("&b          |:_/ |");
        sendMsg("&b         //   \\ \\");
        sendMsg("&b        (|     | )");
        sendMsg("&b       /'\\_   _/`\\");
        sendMsg("&b       \\___)=(___/");
        sendMsg(" ");
        sendMsg(translateMsg("&7* &fAuthors&7: ", getDescription().getAuthors().toString()));
        sendMsg(translateMsg("&7* &fSpigot&7: ", getServer().getName()));
        sendMsg(translateMsg("&7* &fLoaded in&7: ", String.valueOf(System.currentTimeMillis() - timeTracker) + "ms"));
        sendMsg(" ");
        sendMsg("&7&m-----------------------------------------------------");
    }

    private void sendMsg(String msg) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(msg));
    }

    private String translateMsg(String info, String detail) {
        return " &7> &f" + info + ": &b" + detail;
    }


}
