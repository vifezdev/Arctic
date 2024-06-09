package lol.pots.core.note.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.note.menu.NotesMenu;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class NotesCommand {


    @Command(name = "", desc = "a list of a player's notes", usage = "<player>")
    @Require("arctic.note")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
        }
        Player player = (Player) sender;

        new NotesMenu(target).openMenu(player);
    }

}
