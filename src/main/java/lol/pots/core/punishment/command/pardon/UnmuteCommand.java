package lol.pots.core.punishment.command.pardon;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.Punishment;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.punishment.packet.PunishmentPardonPacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.*;

public class UnmuteCommand {

    PunishmentType type = PunishmentType.MUTE;

    @Command(name = "", desc = "unmutes a player", usage = "<player> <reason> [-s]")
    @Require("arctic.unmute")
    public void execute(@Sender CommandSender sender, Profile target, @Text String reason, @Flag('s') boolean silent) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        if (target.getActivePunishment(type) == null) {
            sender.sendMessage(CC.translate("&cThat player isn't " + type.getPardoned() + "."));
            return;
        }
        String pardonedBy;
        if (sender instanceof Player) {
            Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
            pardonedBy = profile.getActiveGrant().getRank().getColor() + sender.getName();
        } else {
            pardonedBy = "&4Console";
        }
        long pardonedOn = System.currentTimeMillis();
        Punishment punishment = target.getActivePunishment(type);
        PunishmentPardonPacket packet = new PunishmentPardonPacket(target.getUuid(), punishment, pardonedBy, reason, pardonedOn, silent);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
