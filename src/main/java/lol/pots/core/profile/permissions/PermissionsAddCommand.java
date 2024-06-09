package lol.pots.core.profile.permissions;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.profile.packet.PermissionsAddPacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class PermissionsAddCommand {

    @Command(name = "add", desc = "adds a permission to a player", usage = "<player> <permission>")
    @Require("permissions.add")
    public void execute(@Sender CommandSender sender, Profile target, String permission) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        if (target.getIndividualPermissions().contains(permission)) {
            sender.sendMessage(CC.translate("&cThis profile already contains that permission."));
            return;
        }
        PermissionsAddPacket packet = new PermissionsAddPacket(target.getUuid(), permission);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
