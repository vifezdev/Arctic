package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankUpdateSuffixPacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class RankSetSuffixCommand {

    @Command(name = "setsuffix", desc = "sets a rank's suffix", usage = "<rank> <suffix>")
    @Require("arctic.rank.setsuffix")
    public void execute(@Sender CommandSender sender, Rank rank, @Text String suffix) {
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        RankUpdateSuffixPacket packet = new RankUpdateSuffixPacket(rank.getName(), suffix);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
