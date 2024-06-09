package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class ReloadCommand {
    @Command(name = "", desc = "Toggles the frozen state of a player")
    @Require("arctic.reload")
    public void execute(@Sender CommandSender commandSender) {
        try {

            Arctic.getInstance().reloadConfigs();
            commandSender.sendMessage(CC.translate("&aSuccessfully reloaded configs."));

        } catch (Exception e) {
            commandSender.sendMessage(CC.translate("&4Error reloading configs."));

        }
    }
}
