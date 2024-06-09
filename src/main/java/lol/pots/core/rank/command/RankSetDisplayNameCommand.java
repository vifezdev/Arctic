package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankUpdateDisplayNamePacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class RankSetDisplayNameCommand {

    @Command(name = "setdisplayname", desc = "sets a rank's prefix", usage = "<rank> <prefix>")
    @Require("arctic.rank.setprefix")
    public void execute(@Sender CommandSender sender, Rank rank, @Text String displayName) {
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        RankUpdateDisplayNamePacket packet = new RankUpdateDisplayNamePacket(rank.getName(), displayName);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
