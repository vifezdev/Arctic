package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class TeleportCommand {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    @Command(name = "", desc = "teleports a player to another player", usage = "<player> [player]")
    @Require("arctic.teleport")
    public void execute(@Sender CommandSender sender, Player target, @OptArg Player target2) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (sender == target) {
            sender.sendMessage(CC.translate("&cYou can't teleport to yourself."));
            return;
        }
        String message;
        Profile targetProfile = profileHandler.getProfileByUUID(target.getUniqueId());
        ;
        if (target2 != null) {
            Profile target2Profile = profileHandler.getProfileByUUID(target2.getUniqueId());
            target.teleport(target2);
            message = "&aTeleported &r{target} &ato &r{target2}&a.";
            sender.sendMessage(CC.translate(message
                    .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())
                    .replace("{target2}", target2Profile.getActiveGrant().getRank().getColor() + target2.getName())));
        } else {
            Player player = (Player) sender;
            player.teleport(target);
            message = "&aTeleported to &r{target}&a.";
            sender.sendMessage(CC.translate(message
                    .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())));
        }
    }

}
