package lol.pots.core.rank.packet;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.redis.packet.Packet;

@Getter
@Setter
public class RankCreatePacket extends Packet {

    private String name;

    public RankCreatePacket(String name) {
        this.name = name;
    }

    @Override
    public void onReceive() {

        String message = "&7[&9Network Update&7] &fCreated the rank &r" + name + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

    @Override
    public void onSend() {

    }

}
