package lol.pots.core.tags.packet;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.handler.TagHandler;
import lol.pots.core.redis.packet.Packet;
import lol.pots.core.tags.Tag;

@Getter
@Setter
public class PrefixDeletePacket extends Packet {

    TagHandler tagHandler = Arctic.getInstance().getTagHandler();

    private String name;

    public PrefixDeletePacket(String name) {
        this.name = name;
    }

    @Override
    public void onReceive() {
        Tag tag = tagHandler.getTagByName(name);
        tagHandler.deleteTag(tag);
        String message = "&7[&9Network Update&7] &fDeleted the prefix &r" + tag.getDisplayName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

    @Override
    public void onSend() {
        Tag tag = tagHandler.getTagByName(name);
        tagHandler.deleteTag(tag);
        String message = "&7[&9Network Update&7] &fDeleted the prefix &r" + tag.getDisplayName() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

}
