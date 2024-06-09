package lol.pots.core.tags.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.tags.Tag;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class TagInformationCommand {

    @Command(name = "info", desc = "shows a prefix's information", usage = "<prefix>")
    @Require("arctic.prefix.info")
    public void execute(@Sender CommandSender sender, Tag tag) {
        if (tag == null) {
            sender.sendMessage(ErrorMessage.PREFIX_NOT_FOUND);
            return;
        }
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bTag Information"));
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate(" &7▪ &fName: &r" + tag.getDisplayName()));
        sender.sendMessage(CC.translate(" &7▪ &fPrefix: &r" + tag.getPrefix()));
        sender.sendMessage(CC.translate(" &7▪ &fSuffix: &f" + tag.getSuffix()));
        sender.sendMessage(CC.CHAT_BAR);
    }

}
