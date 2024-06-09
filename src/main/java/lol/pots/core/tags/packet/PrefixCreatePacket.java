package lol.pots.core.tags.packet;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.redis.packet.Packet;
import lol.pots.core.tags.Tag;

@Getter
@Setter
public class PrefixCreatePacket extends Packet {

    private String name;

    public PrefixCreatePacket(String name) {
        this.name = name;
    }

    @Override
    public void onReceive() {
        Tag tag = new Tag(name);
        tag.save();
        String message = "&7[&9Network Update&7] &fCreated the prefix &r" + tag.getDisplayName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

    @Override
    public void onSend() {

    }

}
