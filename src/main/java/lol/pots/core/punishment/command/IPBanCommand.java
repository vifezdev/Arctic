package lol.pots.core.punishment.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.Punishment;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.punishment.packet.PunishmentAddPacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.TimeUtil;
import lol.pots.core.util.command.annotation.*;

public class IPBanCommand {

    PunishmentType type = PunishmentType.IP_BAN;
    PunishmentType banType = PunishmentType.BAN;

    @Command(name = "", desc = "ip-bans a player", usage = "<player> <duration> <reason> [-s]")
    @Require("arctic.ban")
    public void execute(@Sender CommandSender sender, Profile target, String duration, @Text String reason, @Flag('s') boolean silent) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        if (target.getActivePunishment(type) != null || target.getActivePunishment(banType) != null) {
            sender.sendMessage(CC.translate("&cThat player is already " + type.getContext() + "."));
            return;
        }
        if (TimeUtil.convertStringToLong(duration) == -1) {
            sender.sendMessage(CC.translate("&cInvalid duration length."));
            return;
        }
        int id = target.getPunishments().size() + 1;
        String addedPunishment = type.getName();
        String addedBy;
        if (sender instanceof Player) {
            Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
            addedBy = profile.getActiveGrant().getRank().getColor() + sender.getName();
        } else {
            addedBy = "&4Console";
        }
        long addedOn = System.currentTimeMillis();
        long addedDuration = TimeUtil.convertStringToLong(duration);
        Punishment punishment = new Punishment(id, addedPunishment, addedBy, addedOn, addedDuration, reason, silent, false, "", "", addedOn, silent);
        PunishmentAddPacket packet = new PunishmentAddPacket(target.getUuid(), punishment);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}