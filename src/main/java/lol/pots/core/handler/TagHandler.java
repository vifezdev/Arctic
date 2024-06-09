package lol.pots.core.handler;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import lol.pots.core.Arctic;
import lol.pots.core.tags.Tag;
import lol.pots.core.util.CC;
import lol.pots.core.util.config.ConfigFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TagHandler {

    private List<Tag> tags;

    public TagHandler() {
        tags = new ArrayList<>();
    }

    public void init() {
        for (String key : Arctic.getInstance().getTagConfig().getSection("TAGS")) {
            Tag tag = new Tag(key);

            this.load(tag);
        }
    }

    public void load(Tag tag) {
        FileConfiguration config = Arctic.getInstance().getTagConfig().getConfig();
        String path = "TAGS." + tag.getName();

        tag.setDisplayName(CC.translate(config.getString(path + ".DISPLAY-NAME")));
        tag.setPrefix(CC.translate(config.getString(path + ".PREFIX")));
        tag.setSuffix(CC.translate(config.getString(path + ".SUFFIX")));
    }

    public void save(Tag tag) {
        String path = "TAGS." + tag.getName();

        ConfigFile configFile = Arctic.getInstance().getTagConfig();
        configFile.set(path + ".DISPLAY-NAME", tag.getDisplayName());
        configFile.set(path + ".PREFIX", tag.getPrefix());
        configFile.set(path + ".SUFFIX", tag.getSuffix());
        configFile.save();
    }

    public Tag getTagByName(String name) {
        for (Tag tag : tags) {
            if (tag.getName().equalsIgnoreCase(name)) return tag;
        }
        return null;
    }

    public void deleteTag(Tag tag) {
        tags.remove(tag);
        Arctic.getInstance().getTagConfig().set("TAGS." + tag.getName(), null);
        Arctic.getInstance().getTagConfig().save();
    }

}
