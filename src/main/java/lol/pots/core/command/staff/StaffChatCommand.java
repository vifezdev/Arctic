package lol.pots.core.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.packet.BroadcastStaffMessagePacket;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.*;

public class StaffChatCommand {

    @Command(name = "", desc = "toggles a player's staff chat", usage = "[message]")
    @Require("arctic.staff")
    public void execute(@Sender CommandSender sender, @Text @OptArg String message) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        String format;
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
        if (message != null) {
            format = Arctic.getInstance().getMsgConfig().getString("CHAT-FORMAT.STAFF");
            BroadcastStaffMessagePacket packet = new BroadcastStaffMessagePacket(format
                    .replace("{server}", Bukkit.getServerName())
                    .replace("{staff}", profile.getActiveGrant().getRank().getColor() + sender.getName())
                    .replace("{message}", message));
            Arctic.getInstance().getRedisHandler().sendPacket(packet);
            return;
        }
        if (profile.getStaffSettings().isStaffChat()) {
            System.out.println("Changed to default");
            profile.getStaffSettings().setStaffChat(false);
            profile.save();
        } else {
            System.out.println("Changed to staff");

            profile.getStaffSettings().setStaffChat(true);
            profile.save();
        }
        String context = profile.getStaffSettings().isStaffChat() ? "&aenabled" : "&cdisabled";
        format = "&fYou've &r{context} &fstaff chat.";
        sender.sendMessage(CC.translate(format.replace("{context}", context)));
    }

}