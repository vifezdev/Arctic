package lol.pots.core.command.staff;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

import java.util.ArrayList;
import java.util.List;

public class AltsCommand {

    @Command(name = "", desc = "views a player's alts", usage = "<player>")
    @Require("arctic.alts")
    public void execute(@Sender CommandSender sender, Profile target) {
        if (target.getIpAddresses().isEmpty()) {
            sender.sendMessage(CC.translate(target.getActiveGrant().getRank().getColor() + target.getUsername() + " &chas no alts."));
            return;
        }
        sender.sendMessage(CC.translate(target.getActiveGrant().getRank().getColor() + target.getUsername() + "&f's alts:"));
        List<Profile> alts = Arctic.getInstance().getProfileHandler().getProfilesByLastSeenAddress(target.getLastSeenAddress());
        List<String> profileFancyNames = new ArrayList<>();
        for (Profile profile : alts) {
            if (profile.getUuid().equals(target.getUuid())) continue;
            profileFancyNames.add(profile.getActiveGrant().getRank().getColor() + profile.getUsername());
        }
        sender.sendMessage(CC.translate(StringUtils.join(profileFancyNames, "&f, ")));
    }

}
