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

public class ToggleGlobalChatCommand {

    @Command(name = "", desc = "toggles global chat")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
        profile.getSettings().setGlobalChat(!profile.getSettings().isGlobalChat());
        profile.save();
        String context = profile.getSettings().isGlobalChat() ? "&aenabled" : "&cdisabled";
        String message = "&fYou've &r{context} &fyour global chat.";
        sender.sendMessage(CC.translate(message.replace("{context}", context)));
    }

}
