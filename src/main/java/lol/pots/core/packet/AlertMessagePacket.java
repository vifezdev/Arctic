package lol.pots.core.packet;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.redis.packet.Packet;

@Getter
@Setter
public class AlertMessagePacket extends Packet {

    private String message;

    public AlertMessagePacket(String message) {
        this.message = message;
    }

    @Override
    public void onReceive() {
        Arctic.getInstance().broadcastMessage(message);
    }

    @Override
    public void onSend() {
        Arctic.getInstance().broadcastMessage(message);
    }

}
