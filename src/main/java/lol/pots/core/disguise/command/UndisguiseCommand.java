package lol.pots.core.disguise.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;
import xyz.haoshoku.nick.api.NickAPI;

public class UndisguiseCommand {

    @Command(name = "", desc = "")
    @Require("arctic.disguise")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }

        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());

        if (profile.getDisguiseProfile() == null) {
            sender.sendMessage(CC.translate("&cYou are not disguised."));
            return;
        }

        profile.setDisguiseProcedure(null);
        profile.setDisguiseProfile(null);
        NickAPI.resetNick(profile.getPlayer());
        NickAPI.resetSkin(profile.getPlayer());
        NickAPI.resetUniqueId(profile.getPlayer());
        NickAPI.resetGameProfileName(profile.getPlayer());
        NickAPI.refreshPlayer(profile.getPlayer());
    }

}
