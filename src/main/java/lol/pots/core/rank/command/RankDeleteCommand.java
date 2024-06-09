package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankDeletePacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankDeleteCommand {

    @Command(name = "delete", desc = "deletes a rank", usage = "<rank>")
    @Require("arctic.rank.delete")
    public void execute(@Sender CommandSender sender, Rank rank) {
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        RankDeletePacket packet = new RankDeletePacket(rank.getName());
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
