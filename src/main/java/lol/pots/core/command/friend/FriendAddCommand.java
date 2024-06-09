package lol.pots.core.command.friend;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.fanciful.FancyMessage;

public class FriendAddCommand {

    @Command(name = "add", desc = "")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }
        if (((Player) sender).getUniqueId() == target.getUuid()) {
            sender.sendMessage(CC.translate("&cYou can't friend yourself."));
            return;
        }

        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());

        if (target.getFriendRequests().contains(profile.getUuid())) {
            sender.sendMessage(CC.translate("&cYou already sent that player a friend request."));
            return;
        }
        if (profile.getFriends().contains(target.getUuid())) {
            sender.sendMessage(CC.translate("&cYou're already friends with that player."));
            return;
        }
        target.getFriendRequests().add(profile.getUuid());
        target.save();
        String message = "&fYou've sent a friend request to &r{target}";
        sender.sendMessage(CC.translate(message
                .replace("{target}", target.getActiveGrant().getRank().getColor() + target.getUsername())));

        if (target.getPlayer() != null) {
            FancyMessage clickableMessage = new FancyMessage("[Friend] ").color(ChatColor.GREEN);

            clickableMessage.then(CC.translate(profile.getActiveGrant().getRank().getColor() + profile.getUsername() + " &fhas sent you a friend request! ")).color(ChatColor.WHITE).then("[Accept]").color(ChatColor.GREEN).command("/friend accept " + profile.getUsername()).tooltip(ChatColor.GREEN + "Click to accept");

            clickableMessage.send(target.getPlayer());
        }
    }

}
