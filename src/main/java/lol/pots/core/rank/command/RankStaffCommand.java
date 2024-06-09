package lol.pots.core.rank.command;

import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.packet.RankStaffUpdatePacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankStaffCommand {

    @Command(name = "setstaff", desc = "sets a rank's weight", usage = "<rank>")
    @Require("arctic.rank.setstaff")
    public void execute(@Sender Player sender, Rank rank) {
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(sender.getUniqueId());

        if (rank == null) {
            sender.sendMessage(ErrorMessage.RANK_NOT_FOUND);
            return;
        }
        RankStaffUpdatePacket packet;

        if(rank.isStaff()){
            packet = new RankStaffUpdatePacket(rank.getName(), 0);
            sender.sendMessage(CC.translate(rank.getDisplayName() + " &ais now removed from staff."));

        }
        else{
            packet = new RankStaffUpdatePacket(rank.getName(), 1);
            sender.sendMessage(CC.translate(rank.getDisplayName() + " &ais now set to staff."));

        }


        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
