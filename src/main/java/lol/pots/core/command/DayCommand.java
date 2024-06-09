package lol.pots.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;

public class DayCommand {

    @Command(name = "", desc = "set a player's time to day")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        ((Player) sender).setPlayerTime(0L, false);
        sender.sendMessage(CC.translate("&aIt's now day time."));
    }

}
