package lol.pots.core.command.ignore;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;

public class IgnoreRemoveCommand {

    @Command(name = "remove", desc = "")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        if (((Player) sender).getUniqueId() == target.getUuid()) {
            sender.sendMessage(CC.translate("&cYou can't ignore yourself."));
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
        if (!profile.getIgnored().contains(target.getUuid())) {
            sender.sendMessage(CC.translate("&cThat player isn't ignored."));
            return;
        }
        profile.getIgnored().remove(target.getUuid());
        profile.save();
        String message = "&aYou've removed &r{target} &afrom your ignored list.";
        sender.sendMessage(CC.translate(message
                .replace("{target}", target.getActiveGrant().getRank().getColor() + target.getUsername())));
    }

}
