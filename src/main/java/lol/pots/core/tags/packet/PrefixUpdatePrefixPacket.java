package lol.pots.core.tags.packet;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.handler.TagHandler;
import lol.pots.core.redis.packet.Packet;
import lol.pots.core.tags.Tag;

@Getter
@Setter
public class PrefixUpdatePrefixPacket extends Packet {

    TagHandler tagHandler = Arctic.getInstance().getTagHandler();

    private String name;
    private String prefix1;

    public PrefixUpdatePrefixPacket(String name, String prefix1) {
        this.name = name;
        this.prefix1 = prefix1;
    }

    @Override
    public void onReceive() {
        Tag tag = tagHandler.getTagByName(name);
        tag.setPrefix(prefix1);
        tag.save();
        String message = "&7[&9Network Update&7] &fUpdated the prefix &r" + tag.getDisplayName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

    @Override
    public void onSend() {
        Tag tag = tagHandler.getTagByName(name);
        tag.setPrefix(prefix1);
        tag.save();
        String message = "&7[&9Network Update&7] &fUpdated the prefix &r" + tag.getDisplayName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

}
