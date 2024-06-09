package lol.pots.core.rank.packet;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.handler.RankHandler;
import lol.pots.core.rank.Rank;
import lol.pots.core.redis.packet.Packet;

@Getter
@Setter
public class RankAddPermissionPacket extends Packet {

    private final RankHandler rankHandler = Arctic.getInstance().getRankHandler();

    private String name;
    private String permission;

    public RankAddPermissionPacket(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    @Override
    public void onReceive() {
        Rank rank = rankHandler.getRankByName(name);
        rank.getPermissions().add(permission);
        Arctic.getInstance().getRankHandler().getRanks().forEach(Rank::save);
        String message = "&7[&9Network Update&7] &fUpdated the rank &r" + rank.getColor() + rank.getName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

    @Override
    public void onSend() {
        Rank rank = rankHandler.getRankByName(name);
        rank.getPermissions().add(permission);
        Arctic.getInstance().getRankHandler().getRanks().forEach(Rank::save);
        String message = "&7[&9Network Update&7] &fUpdated the rank &r" + rank.getColor() + rank.getName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

}
