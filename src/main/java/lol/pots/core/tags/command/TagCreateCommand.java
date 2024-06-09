package lol.pots.core.tags.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.tags.Tag;
import lol.pots.core.tags.packet.PrefixCreatePacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class TagCreateCommand {

    @Command(name = "create", desc = "creates a prefix", usage = "<name>")
    @Require("arctic.prefix.create")
    public void execute(@Sender CommandSender sender, String name) {
        if (Arctic.getInstance().getTagHandler().getTagByName(name) != null) {
            sender.sendMessage(CC.translate("&cThis prefix name already exists."));
            return;
        }
        Tag tag = new Tag(name);
        tag.save();
        String message = "&7[&9Network Update&7] &fCreated the prefix &r" + tag.getDisplayName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
        PrefixCreatePacket packet = new PrefixCreatePacket(name);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
