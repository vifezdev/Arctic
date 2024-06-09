package lol.pots.core.grant.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.grant.menu.GrantMenu;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class GrantCommand {

    @Command(name = "", desc = "gives a player a rank", usage = "<player>")
    @Require("arctic.grant")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        new GrantMenu(target.getUuid()).openMenu((Player) sender);
    }

}
