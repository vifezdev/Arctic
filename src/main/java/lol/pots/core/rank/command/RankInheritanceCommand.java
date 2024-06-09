package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankInheritancePacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankInheritanceCommand {

    @Command(name = "inherit", desc = "adds a child rank as an inheritance to a parent rank", usage = "<parent> <child>")
    @Require("arctic.rank.inherit")
    public void execute(@Sender CommandSender sender, Rank parent, Rank child) {
        if (parent == null || child == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        if (parent.getInheritances().contains(child.getName())) {
            sender.sendMessage(CC.translate("&cThis parent rank is already inheriting the child rank."));
            return;
        }
        RankInheritancePacket packet = new RankInheritancePacket(parent.getName(), child.getName());
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
