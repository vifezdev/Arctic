package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.handler.RankHandler;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

import java.util.Collections;
import java.util.Comparator;

public class RanksCommand {

    private final RankHandler rankHandler = Arctic.getInstance().getRankHandler();

    @Command(name = "", desc = "Shows all ranks")
    @Require("arctic.ranks")
    public void execute(@Sender CommandSender sender) {
        rankHandler.getRanks().sort(Comparator.comparingInt(Rank::getWeight));
        Collections.reverse(rankHandler.getRanks());
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&9Rank list"));
        sender.sendMessage(CC.translate("&7" + rankHandler.getRanks().size() + " &9Ranks"));
        sender.sendMessage(CC.CHAT_BAR);
        for (Rank rank : rankHandler.getRanks()) {
            sender.sendMessage(CC.translate("&7- &r" + rank.getDisplayName() + " &f(Weight: " + rank.getWeight() + ")"));
        }
        sender.sendMessage(CC.CHAT_BAR);
    }

}
