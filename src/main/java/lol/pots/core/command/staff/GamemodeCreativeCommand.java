package lol.pots.core.command.staff;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;


public class GamemodeCreativeCommand {

    @Command(name = "", desc = "updates a player's game mode")
    @Require("arctic.gamemode")
    public void execute(@Sender CommandSender sender, @OptArg Player target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (target == null) target = (Player) sender;

        Profile targetProfile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());
        String message = "&aUpdated &r{target}&a's game mode to creative.";
        target.setGameMode(GameMode.CREATIVE);
        sender.sendMessage(CC.translate(message
                .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())));
    }

}
