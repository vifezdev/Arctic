package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.CC;
import lol.pots.core.util.StringUtils;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

import java.util.ArrayList;
import java.util.List;

public class RankInformationCommand {

    @Command(name = "info", desc = "shows a rank's information", usage = "<rank>")
    @Require("arctic.rank.info")
    public void execute(@Sender CommandSender sender, Rank rank) {
        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bRank Information: "));
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&9Name: &r" + rank.getColor() + rank.getName()));
        sender.sendMessage(CC.translate("&9Prefix: &r" + rank.getPrefix()));
        sender.sendMessage(CC.translate("&9Suffix: &r" + rank.getSuffix()));
        sender.sendMessage(CC.translate("&9Weight: &f" + rank.getWeight()));
        sender.sendMessage(CC.translate("&9Durability: &f" + rank.getDurability()));
        sender.sendMessage("");
        sender.sendMessage(CC.translate("&9Permissions &7(" + rank.getPermissions().size() + "):"));
        if (!rank.getPermissions().isEmpty()) {
            sender.sendMessage(CC.translate("&f" + StringUtils.join(rank.getPermissions(), ", ")));
            sender.sendMessage("");
        }
        sender.sendMessage(CC.translate("&9Inheritances &7(" + rank.getInheritances().size() + "):"));
        if (!rank.getInheritances().isEmpty()) {
            List<String> coloredRanks = new ArrayList<>();
            for (String name : rank.getInheritances()) {
                Rank rank1 = Arctic.getInstance().getRankHandler().getRankByName(name);
                coloredRanks.add(rank1.getColor() + rank1.getName());
            }
            sender.sendMessage(CC.translate("&f" + StringUtils.join(coloredRanks, ", ")));
        }
        sender.sendMessage(CC.CHAT_BAR);
    }

}
