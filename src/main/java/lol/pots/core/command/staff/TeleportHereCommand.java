package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class TeleportHereCommand {

    @Command(name = "", desc = "teleports a player to you", usage = "<player>")
    @Require("arctic.teleporthere")
    public void execute(@Sender CommandSender sender, Player target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (sender == target) {
            sender.sendMessage(CC.translate("&cYou can't teleport to yourself."));
            return;
        }
        Player player = (Player) sender;
        Profile targetProfile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());

        if (targetProfile == null) {
            sender.sendMessage(CC.translate("&cCouldn't find profile for " + target.getName()));
            return;
        }

        target.teleport(player);
        String message = "&aTeleported &r{target} &ato you.";
        sender.sendMessage(CC.translate(message.replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())));
    }
}
