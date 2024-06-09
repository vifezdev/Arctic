package lol.pots.core.tags.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.handler.TagHandler;
import lol.pots.core.tags.Tag;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class TagListCommand {

    TagHandler tagHandler = Arctic.getInstance().getTagHandler();

    @Command(name = "list", desc = "shows a list of prefixes")
    @Require("arctic.prefix.list")
    public void execute(@Sender CommandSender sender) {
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bPrefixes List &7(" + tagHandler.getTags().size() + ")"));
        sender.sendMessage(CC.CHAT_BAR);
        for (Tag tag : tagHandler.getTags()) {
            sender.sendMessage(CC.translate("&7- &r" + tag.getDisplayName() + " &7(Prefix: &r" + tag.getPrefix() + "&7)"));
        }
        sender.sendMessage(CC.CHAT_BAR);
    }

}
