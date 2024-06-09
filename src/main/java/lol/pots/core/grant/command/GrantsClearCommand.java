package lol.pots.core.grant.command;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.grant.packet.GrantsClearPacket;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class GrantsClearCommand {

    @Command(name = "clear", desc = "clears a player's grants", usage = "<player>")
    @Require("arctic.grants.clear")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        GrantsClearPacket packet = new GrantsClearPacket(target.getUuid());
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
        sender.sendMessage(CC.translate("&aCleared the grants history of &r{profile}&a.".replace("{profile}", target.getActiveGrant().getRank().getColor() + target.getUsername())));
    }

}
