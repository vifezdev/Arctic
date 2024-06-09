package lol.pots.core.punishment.command.history;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.menu.HistoryMenu;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class HistoryCommand {

    @Command(name = "", desc = "shows a player's history", usage = "<player>")
    @Require("arctic.history")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        new HistoryMenu(target).openMenu((Player) sender);
    }

}
