package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankUpdateDurabilityPacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankSetDurabilityCommand {

    @Command(name = "setdurability", desc = "sets a rank's durability", usage = "<rank> <durability>")
    @Require("arctic.rank.setdurability")
    public void execute(@Sender CommandSender sender, Rank rank, int durability) {
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        RankUpdateDurabilityPacket packet = new RankUpdateDurabilityPacket(rank.getName(), durability);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
