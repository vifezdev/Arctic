package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class CraftCommand {

    @Command(name = "", desc = "opens a crafting bench")
    @Require("arctic.craft")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        Player player = (Player) sender;
        player.openWorkbench(player.getLocation(), true);
    }

}
