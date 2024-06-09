package lol.pots.core.packet;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.redis.packet.Packet;

@Getter
@Setter
public class BroadcastStaffMessagePacket extends Packet {

    private String message;

    public BroadcastStaffMessagePacket(String message) {
        this.message = message;
    }

    @Override
    public void onReceive() {
        Arctic.getInstance().broadcastMessage(message, "arctic.staff");
    }

    @Override
    public void onSend() {
        Arctic.getInstance().broadcastMessage(message, "arctic.staff");
    }

}
