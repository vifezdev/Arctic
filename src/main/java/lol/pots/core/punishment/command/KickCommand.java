package lol.pots.core.punishment.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.Punishment;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.punishment.packet.PunishmentAddPacket;
import lol.pots.core.util.command.annotation.*;

public class KickCommand {

    PunishmentType type = PunishmentType.KICK;

    @Command(name = "", desc = "kicks a player", usage = "<player> <reason> [-s]")
    @Require("arctic.kick")
    public void execute(@Sender CommandSender sender, Profile target, @Text String reason, @Flag('s') boolean silent) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
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
        long addedDuration = Integer.MIN_VALUE;
        Punishment punishment = new Punishment(id, addedPunishment, addedBy, addedOn, addedDuration, reason, silent, false, "", "", addedOn, silent);
        PunishmentAddPacket packet = new PunishmentAddPacket(target.getUuid(), punishment);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
