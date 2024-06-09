package lol.pots.core.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class SudoCommand {
    //TODO: SEND INFO TO TARGET

    @Command(name = "", desc = "make a player say or do something", usage = "<player> <message>")
    @Require("arctic.sudo")

    public void execute(@Sender CommandSender sender, Player target, @Text String message) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
        }
        if (message != null && target.isOnline()) {
            if (message.indexOf("/") == 0) {
                Bukkit.dispatchCommand(target, message.substring(1));
            } else {
                target.chat(message);
            }
        }

    }
}
