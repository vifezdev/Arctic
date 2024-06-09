package lol.pots.core.option.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;

/**
 * @author hieu
 * @date 09/10/2023
 */

public class ToggleSoundsCommand {

    @Command(name = "", desc = "toggles sounds")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
        profile.getSettings().setPrivateMessagingSounds(!profile.getSettings().isPrivateMessagingSounds());
        profile.save();
        String context = profile.getSettings().isPrivateMessagingSounds() ? "&aenabled" : "&cdisabled";
        String message = "&bYou've &r{context} &byour sounds.";
        sender.sendMessage(CC.translate(message.replace("{context}", context)));
    }

}
