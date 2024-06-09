package lol.pots.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.CC;
import lol.pots.core.util.StringUtils;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListCommand {

    @Command(name = "", desc = "shows all online players and ranks")
    public void execute(@Sender CommandSender sender) {
        List<Rank> orderedRanks = Arctic.getInstance().getRankHandler().getRanks();
        orderedRanks.sort(Comparator.comparingInt(Rank::getWeight));
        Collections.reverse(orderedRanks);
        List<String> coloredRankNames = new ArrayList<>();
        for (Rank rank : orderedRanks) {
            coloredRankNames.add(rank.getColor() + rank.getName());
        }
        List<Profile> orderedProfiles = Arctic.getInstance().getProfileHandler().getProfiles();
        orderedProfiles.sort(Comparator.comparingInt(profile -> profile.getActiveGrant().getRank().getWeight()));
        Collections.reverse(orderedProfiles);
        List<String> coloredProfileNames = new ArrayList<>();
        for (Profile profile : orderedProfiles) {
            coloredProfileNames.add(profile.isDisguised() ? profile.getDisguiseProfile().getRank().getColor() + profile.getDisguiseProfile().getName() : profile.getActiveGrant().getRank().getColor() + profile.getUsername());
        }
        sender.sendMessage(CC.translate(StringUtils.join(coloredRankNames, "&f, ")));
        sender.sendMessage(CC.translate("&f(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ") " + StringUtils.join(coloredProfileNames, "&f, ")));
    }

}
