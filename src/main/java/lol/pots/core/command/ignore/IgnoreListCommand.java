package lol.pots.core.command.ignore;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.StringUtils;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IgnoreListCommand {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    @Command(name = "list", desc = "")
    public void execute(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        Profile profile = profileHandler.getProfileByUUID(((Player) sender).getUniqueId());
        List<UUID> ignored = profile.getIgnored();
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bIgnored List &7(" + ignored.size() + "):"));
        sender.sendMessage(CC.CHAT_BAR);
        if (!profile.getIgnored().isEmpty()) {
            List<String> ignoredStrings = new ArrayList<>();
            for (UUID uuid : ignored) {
                Profile eachProfile = profileHandler.getProfileByUUID(uuid);
                ignoredStrings.add(eachProfile.getActiveGrant().getRank().getColor() + eachProfile.getUsername());
            }
            sender.sendMessage(CC.translate("&r" + StringUtils.join(ignoredStrings, ", ")));
            sender.sendMessage(CC.CHAT_BAR);
        }
    }

}
