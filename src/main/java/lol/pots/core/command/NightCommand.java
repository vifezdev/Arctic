package lol.pots.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;

public class NightCommand {

    @Command(name = "", desc = "set a player's time to night")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        ((Player) sender).setPlayerTime(20000L, false);
        sender.sendMessage(CC.translate("&aIt's now night time."));
    }

}
