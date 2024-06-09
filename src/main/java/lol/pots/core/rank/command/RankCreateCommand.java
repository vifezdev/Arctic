package lol.pots.core.rank.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankCreatePacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankCreateCommand {


    @Command(name = "create", desc = "creates a rank", usage = "<name>")
    @Require("arctic.rank.create")
    public void execute(@Sender CommandSender sender, String name) {
        if (Arctic.getInstance().getRankHandler().getRankByName(name) != null) {
            sender.sendMessage(CC.translate("&cThis rank already exists."));
            return;
        }
        Rank rank = new Rank(name);
        Arctic.getInstance().getRankHandler().getRanks().add(rank);
        Arctic.getInstance().getRankHandler().getRanks().forEach(Rank::save);
        String message = "&7[&9Network Update&7] &fCreated the rank &r" + name + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
        RankCreatePacket packet = new RankCreatePacket(name);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
