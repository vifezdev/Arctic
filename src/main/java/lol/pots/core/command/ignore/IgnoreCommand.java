package lol.pots.core.command.ignore;

import org.bukkit.command.CommandSender;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;

public class IgnoreCommand {

    @Command(name = "", desc = "")
    public void execute(@Sender CommandSender sender) {
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bIgnore Commands"));
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate(" &7▪ &f/ignore add &7<player>"));
        sender.sendMessage(CC.translate(" &7▪ &f/ignore remove &7<player>"));
        sender.sendMessage(CC.translate(" &7▪ &f/ignore list"));
        sender.sendMessage(CC.CHAT_BAR);
    }

}
