package lol.pots.core.tags;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.util.CC;

@Getter
@Setter
public class Tag {

    private String name, displayName, prefix, suffix;

    public Tag(String name) {
        this.name = name;
        this.displayName = CC.translate(name);
        prefix = "";
        suffix = "";
        Arctic.getInstance().getTagHandler().getTags().add(this);
    }

    public Tag(String name, String displayName, String prefix, String suffix) {
        this.name = name;
        this.displayName = displayName;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void save() {
        Arctic.getInstance().getTagHandler().save(this);
    }

}
