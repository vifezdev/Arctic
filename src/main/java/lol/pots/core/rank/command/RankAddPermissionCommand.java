package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankAddPermissionPacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankAddPermissionCommand {

    @Command(name = "addperm", desc = "adds a permission to a rank", usage = "<rank> <permission>")
    @Require("arctic.rank.addperm")
    public void execute(@Sender CommandSender sender, Rank rank, String permission) {
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        if (rank.getPermissions().contains(permission)) {
            sender.sendMessage(CC.translate("&cThis rank already contains that permission."));
            return;
        }
        RankAddPermissionPacket packet = new RankAddPermissionPacket(rank.getName(), permission);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
