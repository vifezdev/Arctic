package lol.pots.core.disguise.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.ArcticAPI;
import lol.pots.core.disguise.menu.RankMenu;
import lol.pots.core.disguise.menu.SkinMenu;
import lol.pots.core.disguise.procedure.DisguiseProcedure;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;


public class DisguiseCommand {

    @Command(name = "", desc = "", usage = "<name> [skin]")
    @Require("arctic.disguise")
    public void execute(@Sender CommandSender sender, String name, @OptArg String skin) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());

        if (profile.getDisguiseProfile() != null) {
            sender.sendMessage(CC.translate("&cYou are already disguised."));
            return;
        }

        if (ArcticAPI.getProfileByUsername(name) != null) {
            sender.sendMessage(CC.translate("&cYou cannot disguise as someone that has logged onto the server."));
            return;
        }

        final DisguiseProcedure procedure = new DisguiseProcedure(profile);

        profile.setDisguiseProcedure(procedure);
        profile.getDisguiseProcedure().setName(name);

        if (name == null && skin == null) {
            sender.sendMessage(CC.translate("&cUsage: /disguise <name> [skin]"));
        }

        if (skin != null) {
            profile.getDisguiseProcedure().setSkin(skin);
            new RankMenu().openMenu(profile.getPlayer());
        } else {
            new SkinMenu().openMenu((Player) sender);
        }
    }

}
