package lol.pots.core.command.staff;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class MoreCommand {

    @Command(name = "", desc = "gives a player more")
    @Require("arctic.more")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        Player player = (Player) sender;
        if (player.getItemInHand().getType() == Material.AIR) {
            sender.sendMessage(CC.translate("&cYou aren't holding anything in your hand."));
            return;
        }
        player.getItemInHand().setAmount(64);
        sender.sendMessage(CC.translate("&fSet the " + player.getItemInHand().toString() + " &fin your hand to &b64"));
    }

}
