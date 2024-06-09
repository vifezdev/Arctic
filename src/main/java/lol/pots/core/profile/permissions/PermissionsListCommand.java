package lol.pots.core.profile.permissions;

import org.bukkit.command.CommandSender;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.StringUtils;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class PermissionsListCommand {

    @Command(name = "list", desc = "shows a player's permissions", usage = "<player>")
    @Require("permissions.list")
    public void execute(@Sender CommandSender sender, Profile profile) {
        if (profile == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bPermissions List &7(" + profile.getIndividualPermissions().size() + "):"));
        sender.sendMessage(CC.CHAT_BAR);
        if (!profile.getIndividualPermissions().isEmpty()) {
            sender.sendMessage(CC.translate("&f" + StringUtils.join(profile.getIndividualPermissions(), ", ")));
            sender.sendMessage(CC.CHAT_BAR);
        }
    }

}
