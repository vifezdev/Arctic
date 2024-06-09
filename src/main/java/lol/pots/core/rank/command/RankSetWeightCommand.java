package lol.pots.core.rank.command;

import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankUpdateWeightPacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankSetWeightCommand {

    @Command(name = "setweight", desc = "sets a rank's weight", usage = "<rank> <weight>")
    @Require("arctic.rank.setweight")
    public void execute(@Sender Player sender, Rank rank, int weight) {
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(sender.getUniqueId());

        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        if (profile.getActiveGrant().getRank().getWeight() < weight) {
            sender.sendMessage(CC.translate("&cYou cannot set the weight of a rank higher than that of your own."));
        }

        RankUpdateWeightPacket packet = new RankUpdateWeightPacket(rank.getName(), weight);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
