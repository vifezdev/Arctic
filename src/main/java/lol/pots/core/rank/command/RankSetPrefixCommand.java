package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankUpdatePrefixPacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class RankSetPrefixCommand {

    @Command(name = "setprefix", desc = "sets a rank's prefix", usage = "<rank> <prefix>")
    @Require("arctic.rank.setprefix")
    public void execute(@Sender CommandSender sender, Rank rank, @Text String prefix) {
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        RankUpdatePrefixPacket packet = new RankUpdatePrefixPacket(rank.getName(), prefix);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
