package lol.pots.core.command.staff;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class GamemodeCommand {

    @Command(name = "", desc = "updates a player's game mode")
    @Require("arctic.gamemode")
    public void execute(@Sender Player sender, @OptArg Player target, String gamemodeString) {
        if (target == null) {
            target = sender;
        }
        Profile targetProfile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());
        String message = "&aUpdated &r{target}&a's game mode to {gamemode}.";
        switch (gamemodeString) {
            case "creative":
            case "1":
                target.setGameMode(GameMode.CREATIVE);
                sender.sendMessage(CC.translate(message
                        .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())
                        .replace("{gamemode}", "creative")));
                break;
            case "survival":
            case "0":
                target.setGameMode(GameMode.SURVIVAL);
                sender.sendMessage(CC.translate(message
                        .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())
                        .replace("{gamemode}", "survival")));
                break;
            case "adventure":
            case "2":
                target.setGameMode(GameMode.ADVENTURE);
                sender.sendMessage(CC.translate(message
                        .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())
                        .replace("{gamemode}", "adventure")));
                break;
            default:
                sender.sendMessage(CC.translate("&cInvalid game mode type."));
                break;
        }
    }

}
