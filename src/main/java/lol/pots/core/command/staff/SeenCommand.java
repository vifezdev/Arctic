package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.TimeUtil;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class SeenCommand {

    @Command(name = "", desc = "shows when a player was last online", usage = "<player>")
    @Require("arctic.seen")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        String message;
        if (!target.getLastSeenServer().equals("None")) {
            message = "&r{profile} &fis currently on &a{server}&e.";
            sender.sendMessage(CC.translate(message
                    .replace("{profile}", target.getActiveGrant().getRank().getColor() + target.getUsername())
                    .replace("{server}", target.getLastSeenServer())));
        } else {
            message = "&r{profile} &fwas last seen &b{seen} &fago.";
            sender.sendMessage(CC.translate(message
                    .replace("{profile}", target.getActiveGrant().getRank().getColor() + target.getUsername())
                    .replace("{seen}", TimeUtil.convertLongToString(System.currentTimeMillis() - target.getLastSeenOn()))));
        }
    }

}
