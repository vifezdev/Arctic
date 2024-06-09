package lol.pots.core.rank.packet;

import lol.pots.core.Arctic;
import lol.pots.core.rank.Rank;
import lol.pots.core.redis.packet.Packet;

public class RankStaffUpdatePacket extends Packet {

    String rankName;
    int addOrRemove;


    public RankStaffUpdatePacket(String rankName, int addOrRemove ){
        this.rankName = rankName;
        this.addOrRemove = addOrRemove;

    }
    @Override
    public void onReceive() {
        Arctic.getInstance().getRankHandler().getRankByName(rankName).setStaff(addOrRemove != 0);
        Arctic.getInstance().getRankHandler().getRanks().forEach(Rank::save);

    }

    @Override
    public void onSend() {
        Arctic.getInstance().getRankHandler().getRankByName(rankName).setStaff(addOrRemove != 0);
        Arctic.getInstance().getRankHandler().getRanks().forEach(Rank::save);

    }
}
