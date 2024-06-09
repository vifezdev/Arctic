package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.handler.RankHandler;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankCommand {

    private final RankHandler rankHandler = Arctic.getInstance().getRankHandler();

    @Command(name = "", desc = "shows rank commands")
    @Require("arctic.rank")
    public void execute(@Sender CommandSender sender) {
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bRank Commands"));
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate(" &7▪ &f/rank create &7<name>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank delete &7<rank>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank info &7<rank>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank setprefix &7<rank> <prefix>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank setsuffix &7<rank> <suffix>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank setweight &7<rank> <weight>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank setstaff &7<rank>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank setcolor &7<rank> <color>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank addperm &7<rank> <permission>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank delperm &7<rank> <permission>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank inherit &7<parent> <child>"));
        sender.sendMessage(CC.translate(" &7▪ &f/rank uninherit &7<parent> <child>"));
        sender.sendMessage(CC.CHAT_BAR);
    }

}
