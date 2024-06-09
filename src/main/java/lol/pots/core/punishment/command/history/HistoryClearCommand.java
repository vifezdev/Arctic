package lol.pots.core.punishment.command.history;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.packet.PunishmentClearPacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class HistoryClearCommand {

    @Command(name = "clear", desc = "clears a player's history", usage = "<player>")
    @Require("arctic.history.clear")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        PunishmentClearPacket packet = new PunishmentClearPacket(target.getUuid());
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
        sender.sendMessage(CC.translate("&aCleared the punishments history of &r{profile}&a.".replace("{profile}", target.getActiveGrant().getRank().getColor() + target.getUsername())));
    }

}
