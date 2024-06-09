package lol.pots.core.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class SudoAllCommand {
    //TODO: SEND INFO TO TARGET

    @Command(name = "", desc = "sudos everone", usage = "<message>")
    @Require("arctic.sudoall")

    public void execute(@Sender CommandSender sender, @Text String message) {
        boolean isCommand = false;

        if (message.indexOf("/") == 0) isCommand = true;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (isCommand) Bukkit.dispatchCommand(p, message.substring(1));
            else p.chat(message);
        }
    }
}
