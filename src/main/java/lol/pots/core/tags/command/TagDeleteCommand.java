package lol.pots.core.tags.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.tags.Tag;
import lol.pots.core.tags.packet.PrefixDeletePacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class TagDeleteCommand {

    @Command(name = "delete", desc = "deletes a prefix", usage = "<prefix>")
    @Require("arctic.prefix.delete")
    public void execute(@Sender CommandSender sender, Tag tag) {
        if (tag == null) {
            sender.sendMessage(ErrorMessage.PREFIX_NOT_FOUND);
            return;
        }
        PrefixDeletePacket packet = new PrefixDeletePacket(tag.getName());
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
