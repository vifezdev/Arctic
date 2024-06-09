package lol.pots.core.redis.packet;

public abstract class Packet {

    public abstract void onReceive();

    public abstract void onSend();

}
