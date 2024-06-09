package lol.pots.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.packet.BroadcastRequestPacket;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class RequestCommand {

    @Command(name = "", desc = "requests for assistance", usage = "<reason>")
    public void execute(@Sender CommandSender sender, @Text String reason) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
        if (profile.isOnRequestCooldown()) {
            sender.sendMessage(CC.translate("&cYou're currently on a request cooldown. Please try again later."));
            return;
        }
        profile.setLastRequestOn(System.currentTimeMillis());
        sender.sendMessage(CC.translate("&aYou've submitted a request to all online staff members."));
        String message = Arctic.getInstance().getMsgConfig().getString("STAFF.REQUEST.MESSAGE");
        BroadcastRequestPacket packet = new BroadcastRequestPacket(CC.translate(message
                .replace("{server}", Bukkit.getServerName())
                .replace("{profile}", profile.getActiveGrant().getRank().getColor() + sender.getName())
                .replace("{reason}", reason)));
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
