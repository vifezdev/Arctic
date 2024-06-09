package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankUpdateColorPacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankSetColorCommand {

    @Command(name = "setcolor", desc = "sets a rank's color", usage = "<rank> <color>")
    @Require("arctic.rank.setcolor")
    public void execute(@Sender CommandSender sender, Rank rank, String color) {
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        RankUpdateColorPacket packet = new RankUpdateColorPacket(rank.getName(), color);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
