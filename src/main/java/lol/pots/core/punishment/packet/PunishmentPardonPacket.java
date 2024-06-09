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
 * @date 06/10/2023
 */

@Getter
@Setter
public class PunishmentPardonPacket extends Packet {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private UUID target;
    private Punishment punishment;
    private String pardonedBy;
    private String pardonedReason;
    private long pardonedOn;
    private boolean silent;

    public PunishmentPardonPacket(UUID target, Punishment punishment, String pardonedBy, String pardonedReason, long pardonedOn, boolean silent) {
        this.target = target;
        this.punishment = punishment;
        this.pardonedBy = pardonedBy;
        this.pardonedReason = pardonedReason;
        this.pardonedOn = pardonedOn;
        this.silent = silent;
    }

    @Override
    public void onReceive() {
        Profile profile = profileHandler.getProfileByUUID(target);
        PunishmentType type = punishment.getPunishmentType();
        for (Punishment punishment1 : profile.getPunishmentsList(type)) {
            if (punishment1.getAddedOn() == punishment.getAddedOn()) {
                punishment1.setPardoned(true);
                punishment1.setPardonedBy(pardonedBy);
                punishment1.setPardonedReason(pardonedReason);
                punishment1.setPardonedOn(pardonedOn);
            }
        }
        profile.save();
        String message = silent ? Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.SILENT-BROADCAST") : Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BROADCAST");
        if (silent) {
            Arctic.getInstance().broadcastMessage(message
                    .replace("{target}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                    .replace("{context}", punishment.getPunishmentType().getPardoned())
                    .replace("{issuer}", punishment.getAddedBy()), "arctic.staff");
        } else {
            Arctic.getInstance().broadcastMessage(message
                    .replace("{target}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                    .replace("{context}", punishment.getPunishmentType().getPardoned())
                    .replace("{issuer}", punishment.getAddedBy()));
        }
        String punishMessage;
        switch (punishment.getPunishmentType()) {
            case MUTE:
                if (Bukkit.getPlayer(target) == null) return;
                punishMessage = "&cYou've been unmuted by &r{issuer}&c.\nReason: &e{reason}&c.";
                Bukkit.getPlayer(target).sendMessage(CC.translate(punishMessage
                        .replace("{issuer}", punishment.getAddedBy())
                        .replace("{reason}", punishment.getAddedReason())));
                break;
            case WARN:
                if (Bukkit.getPlayer(target) == null) return;
                punishMessage = "&cYou've been unwarned by &r{issuer}&c.\nReason: &e{reason}&c.";
                Bukkit.getPlayer(target).sendMessage(CC.translate(punishMessage
                        .replace("{issuer}", punishment.getAddedBy())
                        .replace("{reason}", punishment.getAddedReason())));
                break;
        }
    }

    @Override
    public void onSend() {
        Profile profile = profileHandler.getProfileByUUID(target);
        PunishmentType type = punishment.getPunishmentType();
        for (Punishment punishment1 : profile.getPunishmentsList(type)) {
            if (punishment1.getAddedOn() == punishment.getAddedOn()) {
                punishment1.setPardoned(true);
                punishment1.setPardonedBy(pardonedBy);
                punishment1.setPardonedReason(pardonedReason);
                punishment1.setPardonedOn(pardonedOn);
            }
        }
        profile.save();
        String message = silent ? Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.SILENT-CONTEXT") : Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.CONTEXT");
        if (silent) {
            Arctic.getInstance().broadcastMessage(message
                    .replace("{target}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                    .replace("{context}", punishment.getPunishmentType().getPardoned())
                    .replace("{issuer}", punishment.getAddedBy()), "arctic.staff");
        } else {
            Arctic.getInstance().broadcastMessage(message
                    .replace("{target}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                    .replace("{context}", punishment.getPunishmentType().getPardoned())
                    .replace("{issuer}", punishment.getAddedBy()));
        }
        String punishMessage;
        switch (punishment.getPunishmentType()) {
            case MUTE:
                if (Bukkit.getPlayer(target) == null) return;
                punishMessage = "&cYou've been unmuted by &r{issuer}&c.\nReason: &e{reason}&c.";
                Bukkit.getPlayer(target).sendMessage(CC.translate(punishMessage
                        .replace("{issuer}", punishment.getAddedBy())
                        .replace("{reason}", punishment.getPardonedReason())));
                break;
            case WARN:
                if (Bukkit.getPlayer(target) == null) return;
                punishMessage = "&cYou've been unwarned by &r{issuer}&c.\nReason: &e{reason}&c.";
                Bukkit.getPlayer(target).sendMessage(CC.translate(punishMessage
                        .replace("{issuer}", punishment.getAddedBy())
                        .replace("{reason}", punishment.getPardonedReason())));
                break;
        }
    }

}
