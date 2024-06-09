package lol.pots.core.command.friend;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;

public class FriendAcceptCommand {

    @Command(name = "accept", desc = "")
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
        if (!profile.getFriendRequests().contains(target.getUuid())) {
            sender.sendMessage(CC.translate("&cThat player has not sent you a friend request."));
            return;
        }
        if (profile.getFriends().contains(target.getUuid())) {
            sender.sendMessage(CC.translate("&cYou're already friends with that player."));
            return;
        }
        target.getFriends().add(profile.getUuid());
        profile.getFriends().add(target.getUuid());

        if (target.getPlayer() != null) {
            target.getPlayer().sendMessage(CC.translate("&aYou are now friends with " + profile.getFancyUsername() + "!"));
        }
        profile.getPlayer().sendMessage(CC.translate("&aYou are now friends with " + target.getFancyUsername() + "!"));
    }

}
