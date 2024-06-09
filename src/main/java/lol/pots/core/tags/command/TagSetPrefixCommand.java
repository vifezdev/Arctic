package lol.pots.core.tags.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.tags.Tag;
import lol.pots.core.tags.packet.PrefixUpdatePrefixPacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class TagSetPrefixCommand {

    @Command(name = "setprefix", desc = "sets a prefix's prefix", usage = "<prefix> <prefix>")
    @Require("arctic.prefix.setprefix")
    public void execute(@Sender CommandSender sender, Tag tag, @Text String prefix1) {
        if (tag == null) {
            sender.sendMessage(ErrorMessage.PREFIX_NOT_FOUND);
            return;
        }
        PrefixUpdatePrefixPacket packet = new PrefixUpdatePrefixPacket(tag.getName(), prefix1);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
