package lol.pots.core.tags.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class TagCommand {

    @Command(name = "", desc = "shows all prefixes")
    @Require("arctic.tag")
    public void execute(@Sender CommandSender sender) {
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bTag Commands"));
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate(" &7▪ &f/tag create &7<name>"));
        sender.sendMessage(CC.translate(" &7▪ &f/tag delete &7<name>"));
//        sender.sendMessage(CC.translate(" &7▪ &f/tag info &7<rank>")); Useless
        sender.sendMessage(CC.translate(" &7▪ &f/tag list"));
        sender.sendMessage(CC.translate(" &7▪ &f/tag setcolor &7<tag> <color>"));
        sender.sendMessage(CC.translate(" &7▪ &f/tag setprefix &7<rank> <prefix>"));
        sender.sendMessage(CC.translate(" &7▪ &f/tag setitem &7<rank>"));
        sender.sendMessage(CC.CHAT_BAR);
    }

}
