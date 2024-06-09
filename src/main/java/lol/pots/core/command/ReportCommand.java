package lol.pots.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.packet.BroadcastReportPacket;
import lol.pots.core.profile.Profile;
import lol.pots.core.report.menu.ReportMenu;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class ReportCommand {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    @Command(name = "", desc = "reports a player", usage = "<player> [reason]")
    public void execute(@Sender CommandSender sender, Player player, @Text @OptArg String reason) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }

        Profile profile = profileHandler.getProfileByUUID(((Player) sender).getUniqueId());
        if (profile.isOnReportCooldown()) {
            sender.sendMessage(CC.translate("&cYou're currently on a report cooldown. Try again later!"));
            return;
        }
        if (reason.isEmpty()) {
            new ReportMenu(player.getUniqueId()).openMenu((Player) sender);
            return;
        }
        Profile target = profileHandler.getProfileByUUID(player.getUniqueId());
        profile.setLastReportOn(System.currentTimeMillis());
        profile.save();
        sender.sendMessage(CC.translate("&aYou've submitted a report to all online staff members."));
        String message = Arctic.getInstance().getMsgConfig().getString("STAFF.REPORT.MESSAGE");
        BroadcastReportPacket packet = new BroadcastReportPacket(CC.translate(message
                .replace("{server}", Bukkit.getServerName())
                .replace("{profile}", profile.getActiveGrant().getRank().getColor() + sender.getName())
                .replace("{target}", target.getActiveGrant().getRank().getColor() + player.getName())
                .replace("{reason}", reason)));
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
