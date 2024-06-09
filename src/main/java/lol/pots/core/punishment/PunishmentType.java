package lol.pots.core.punishment;

import lombok.Getter;

@Getter
public enum PunishmentType {

    BLACKLIST("Blacklist", "blacklisted", "unblacklisted", "&4", 14),
    BAN("Ban", "banned", "unbanned", "&c", 14),
    IP_BAN("IP Ban", "ipbanned", "unbanned", "&c", 14),
    KICK("Kick", "kicked", "", "&6", 1),
    MUTE("Mute", "muted", "unmuted", "&e", 4),
    WARN("Warning", "warned", "unwarned", "&a", 5);

    private final String name, context, pardoned, color;
    private final int durability;

    PunishmentType(String name, String context, String pardoned, String color, int durability) {
        this.name = name;
        this.context = context;
        this.pardoned = pardoned;
        this.color = color;
        this.durability = durability;
    }

    public static PunishmentType getPunishmentTypeByName(String name) {
        for (PunishmentType punishmentType : values()) {
            if (punishmentType.getName().equals(name)) return punishmentType;
        }
        return null;
    }

}
