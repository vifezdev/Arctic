package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class FlyCommand {

    @Command(name = "", desc = "toggles a player's flight")
    @Require("arctic.fly")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        Player player = (Player) sender;
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
        } else {
            player.setAllowFlight(true);
            player.setFlying(true);
        }
        String message = "&bYou've &r{context} &bflight mode.";
        String context = player.getAllowFlight() ? "&aenabled" : "&cdisabled";
        sender.sendMessage(CC.translate(message.replace("{context}", context)));
    }

}
