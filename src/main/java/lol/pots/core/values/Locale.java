package lol.pots.core.values;

import org.bukkit.configuration.file.FileConfiguration;
import lol.pots.core.Arctic;

import java.util.List;

public enum Locale {

    STAFF_JOIN("settings", "STAFF.JOIN"),
    STAFF_SWITCH("settings", "STAFF.SWITCH"),
    STAFF_LEAVE("settings", "STAFF.LEAVE"),

    STAFF_REQUEST_COOLDOWN("settings", "STAFF.REQUEST.COOL-DOWN"),
    STAFF_REQUEST_MESSAGE("settings", "STAFF.REQUEST.MESSAGE"),
    STAFF_REPORT_COOLDOWN("settings", "STAFF.REPORT.COOL-DOWN"),
    STAFF_REPORT_MESSAGE("settings", "STAFF.REPORT.MESSAGE"),
    CHAT_FORMAT_STAFF("settings", "CHAT-FORMAT.STAFF"),
    CHAT_FORMAT_DEFAULT("settings", "STAFF.CHAT-FORMAT.DEFAULT"),
    CONVERSATIONS_TO("settings", "STAFF.CONVERSATIONS.TO"),

    FROZEN_STAFF_FREEZE_MESSAGE("messages", "FROZEN.staff-freeze-message"),
    FROZEN_STAFF_UNFREEZE_MESSAGE("messages", "FROZEN.staff-unfreeze-message"),
    FROZEN_FREEZE_MESSAGE("messages", "FROZEN.freeze-message"),
    FRIEND_PREFIX("messages", "FRIEND.PREFIX"),
    FRIEND_JOIN("messages", "FRIEND.JOIN"),
    CONVERSATIONS_FROM("messages", "STAFF.CONVERSATIONS.FROM"),

    SELF_VANISH("messages", "VANISH.SELF_VANISH"),
    SELF_UNVANISH("messages", "VANISH.SELF_UNVANISH"),
    OTHER_VANISH("messages", "VANISH.OTHER_VANISH"),
    OTHER_UNVANISH("messages", "VANISH.OTHER_UNVANISH"),

    PING_SELF("messages","PING.SELF"),

    PING_OTHER("messages","PING.OTHER"),
    SERVERKICK("messages", "PUNISHMENTS.SERVER-KICK"),
    FRIEND_LEAVE("settings", "FRIEND.LEAVE"),

    SELF_FILTER("messages", "FILTER.SELF_FILTER"),
    LADDER_PUNISHMENTS("ladder", "LADDER");





    private final String configName;
    private final String path;

    Locale(String configName, String path) {
        this.configName = configName;
        this.path = path;

    }
    public String getString(){
        FileConfiguration config = Arctic.getInstance().getConfigByName(configName).getConfig();
        return config.getString(path);
    }

    public int getInt(){
        FileConfiguration config = Arctic.getInstance().getConfigByName(configName).getConfig();
        return config.getInt(path);
    }

    public double getDouble(){
        FileConfiguration config = Arctic.getInstance().getConfigByName(configName).getConfig();
        return config.getDouble(path);
    }

    public boolean getBoolean(){
        FileConfiguration config = Arctic.getInstance().getConfigByName(configName).getConfig();
        return config.getBoolean(path);
    }

    public List<?> getList(){
        FileConfiguration config = Arctic.getInstance().getConfigByName(configName).getConfig();
        return config.getList(path);
    }

    public Object get() {
        FileConfiguration config = Arctic.getInstance().getConfigByName(configName).getConfig();
        if (config.isString(path)) {
            return config.getString(path);
        } else if (config.isInt(path)) {
            return config.getInt(path);
        } else if (config.isDouble(path)) {
            return config.getDouble(path);
        } else if (config.isBoolean(path)) {
            return config.getBoolean(path);
        } else if (config.isList(path)) {
            return config.getList(path);
        }
        return null;
    }

    public Object setConfigValue(Object value) {
        FileConfiguration config = Arctic.getInstance().getConfigByName(configName).getConfig();
        if (config.isString(path)) {
            config.set(path, value);
            return config.getString(path);
        } else if (config.isInt(path)) {
            config.set(path, value);
            return config.getInt(path);
        } else if (config.isDouble(path)) {
            config.set(path, value);
            return config.getDouble(path);
        } else if (config.isBoolean(path)) {
            config.set(path, value);
            return config.getBoolean(path);
        } else if (config.isList(path)) {
            config.set(path, value);
            return config.getList(path);
        }
        return null;
    }
}