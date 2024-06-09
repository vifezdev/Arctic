package lol.pots.core.grant.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.grant.Grant;
import lol.pots.core.grant.packet.GrantAddPacket;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.CC;
import lol.pots.core.util.TimeUtil;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class oGrantCommand {

    @Command(name = "", desc = "gives a player a rank", usage = "<player> <rank> <duration> <reason>")
    @Require("arctic.grant")
    public void execute(@Sender CommandSender sender, Profile target, Rank rank, String duration, @Text String reason) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        if (TimeUtil.convertStringToLong(duration) == -1) {
            sender.sendMessage(CC.translate("&cInvalid duration length."));
            return;
        }
        int id = target.getGrants().size() + 1;
        String addedRank = rank.getName();
        String addedBy;
        if (sender instanceof Player) {
            Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
            addedBy = profile.getActiveGrant().getRank().getColor() + sender.getName();
        } else {
            addedBy = "&4Console";
        }
        long addedOn = System.currentTimeMillis();
        long addedDuration = TimeUtil.convertStringToLong(duration);
        Grant grant = new Grant(id, addedRank, addedBy, addedOn, addedDuration, reason, false, "", "", addedOn);
        sender.sendMessage(CC.translate("&aSuccessfully granted " + target.getUsername() + " &athe " + rank.getColor() + rank.getName() + " &arank for " + grant.getExpiresInDurationText() + "&a."));
        GrantAddPacket packet = new GrantAddPacket(target.getUuid(), grant);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
