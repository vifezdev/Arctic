package lol.pots.core.rank.packet;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.handler.RankHandler;
import lol.pots.core.rank.Rank;
import lol.pots.core.redis.packet.Packet;

@Getter
@Setter
public class RankDeletePacket extends Packet {

    private final RankHandler rankHandler = Arctic.getInstance().getRankHandler();

    private String name;

    public RankDeletePacket(String name) {
        this.name = name;
    }

    @Override
    public void onReceive() {
        Rank rank = rankHandler.getRankByName(name);
        rankHandler.deleteRank(rank);
        rankHandler.getRanks().remove(rank);
        Arctic.getInstance().getRankConfig().set("ranks." + getName(), null);
        Arctic.getInstance().getRankConfig().save();
        String message = "&7[&9Network Update&7] &fDeleted the rank &r" + rank.getColor() + rank.getName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

    @Override
    public void onSend() {
        Rank rank = rankHandler.getRankByName(name);
        String message = "&7[&9Network Update&7] &fDeleted the rank &r" + rank.getColor() + rank.getName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
        rankHandler.deleteRank(rank);
    }

}
