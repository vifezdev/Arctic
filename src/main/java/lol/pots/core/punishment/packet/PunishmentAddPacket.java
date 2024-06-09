package lol.pots.core.punishment.packet;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.Punishment;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.redis.packet.Packet;
import lol.pots.core.util.CC;

import java.util.UUID;

/**
 * @author hieu
 * @date 05/10/2023
 */

@Getter
@Setter
public class PunishmentAddPacket extends Packet {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private UUID target;
    private Punishment punishment;

    public PunishmentAddPacket(UUID target, Punishment punishment) {
        this.target = target;
        this.punishment = punishment;
    }

    @Override
    public void onReceive() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getPunishments().add(punishment);
        profile.save();
        String message = punishment.isAddedSilent() ? Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.SILENT-BROADCAST") : Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BROADCAST");
        if (punishment.isAddedSilent()) {
            Arctic.getInstance().broadcastMessage(message
                    .replace("{target}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                    .replace("{context}", punishment.getPunishmentType().getContext())
                    .replace("{issuer}", punishment.getAddedBy()), "arctic.staff");
        } else {
            Arctic.getInstance().broadcastMessage(message
                    .replace("{target}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                    .replace("{context}", punishment.getPunishmentType().getContext())
                    .replace("{issuer}", punishment.getAddedBy()));
        }
        String kickMessage;
        String punishMessage;
        switch (punishment.getPunishmentType()) {
            case BLACKLIST:
                if (Bukkit.getPlayer(target) == null) return;
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BLACKLIST");
                Bukkit.getPlayer(target).kickPlayer(CC.translate(kickMessage
                        .replace("{reason}", profile.getActivePunishment(PunishmentType.BLACKLIST).getAddedReason())));
                break;
            case BAN:
                if (Bukkit.getPlayer(target) == null) return;
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BAN");
                Bukkit.getPlayer(target).kickPlayer(CC.translate(kickMessage.replace("{reason}", profile.getActivePunishment(PunishmentType.BAN).getAddedReason()).replace("{duration}", punishment.getExpiresInDurationText())));
                break;
            case IP_BAN:
                if (Bukkit.getPlayer(target) == null) return;
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.IP-BAN");
                Bukkit.getPlayer(target).kickPlayer(CC.translate(kickMessage.replace("{reason}", profile.getActivePunishment(PunishmentType.IP_BAN).getAddedReason()).replace("{duration}", punishment.getExpiresInDurationText())));
                break;
            case KICK:
                if (Bukkit.getPlayer(target) == null) return;
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.KICK");
                Bukkit.getPlayer(target).kickPlayer(CC.translate(kickMessage.replace("{reason}", punishment.getAddedReason())));
                break;
            case MUTE:
                if (Bukkit.getPlayer(target) == null) return;
                punishMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.MUTE");
                Bukkit.getPlayer(target).sendMessage(CC.translate(punishMessage
                        .replace("{issuer}", punishment.getAddedBy())
                        .replace("{duration}", punishment.getAddedDurationText())
                        .replace("{reason}", punishment.getAddedReason())));
                break;
            case WARN:
                if (Bukkit.getPlayer(target) == null) return;
                punishMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.WARN");
                Bukkit.getPlayer(target).sendMessage(CC.translate(punishMessage
                        .replace("{issuer}", punishment.getAddedBy())
                        .replace("{reason}", punishment.getAddedReason())));
                break;
        }
    }

    @Override
    public void onSend() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getPunishments().add(punishment);
        profile.save();
        String message = punishment.isAddedSilent() ? Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.SILENT-CONTEXT") : Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.CONTEXT");
        if (punishment.isAddedSilent()) {
            Arctic.getInstance().broadcastMessage(message
                    .replace("{target}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                    .replace("{context}", punishment.getPunishmentType().getContext())
                    .replace("{issuer}", punishment.getAddedBy()), "arctic.staff");
        } else {
            Arctic.getInstance().broadcastMessage(message
                    .replace("{target}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                    .replace("{context}", punishment.getPunishmentType().getContext())
                    .replace("{issuer}", punishment.getAddedBy()));
        }
        String kickMessage;
        String punishMessage;
        switch (punishment.getPunishmentType()) {
            case BLACKLIST:
                if (Bukkit.getPlayer(target) == null) return;
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BLACKLIST");
                Bukkit.getPlayer(target).kickPlayer(CC.translate(kickMessage
                        .replace("{reason}", profile.getActivePunishment(PunishmentType.BLACKLIST).getAddedReason())));
                break;
            case BAN:
                if (Bukkit.getPlayer(target) == null) return;
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BAN");
                Bukkit.getPlayer(target).kickPlayer(CC.translate(kickMessage.replace("{reason}", profile.getActivePunishment(PunishmentType.BAN).getAddedReason()).replace("{duration}", punishment.getExpiresInDurationText())));
                break;
            case IP_BAN:
                if (Bukkit.getPlayer(target) == null) return;
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.IP-BAN");
                Bukkit.getPlayer(target).kickPlayer(CC.translate(kickMessage.replace("{reason}", profile.getActivePunishment(PunishmentType.IP_BAN).getAddedReason()).replace("{duration}", punishment.getExpiresInDurationText())));
                break;
            case KICK:
                if (Bukkit.getPlayer(target) == null) return;
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.KICK");
                Bukkit.getPlayer(target).kickPlayer(CC.translate(kickMessage.replace("{reason}", punishment.getAddedReason())));
                break;
            case MUTE:
                if (Bukkit.getPlayer(target) == null) return;
                punishMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.MUTE");
                Bukkit.getPlayer(target).sendMessage(CC.translate(punishMessage
                        .replace("{issuer}", punishment.getAddedBy())
                        .replace("{duration}", punishment.getExpiresInDurationText())
                        .replace("{reason}", punishment.getAddedReason())));
                break;
            case WARN:
                if (Bukkit.getPlayer(target) == null) return;
                punishMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.WARN");
                Bukkit.getPlayer(target).sendMessage(CC.translate(punishMessage
                        .replace("{issuer}", punishment.getAddedBy())
                        .replace("{reason}", punishment.getAddedReason())));
                break;
        }
    }

}
