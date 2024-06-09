package lol.pots.core.ladders;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Ladder {
    private java.lang.String name;
    List<String> punishments;

    public Ladder(java.lang.String name) {
        this.name = name;
        punishments = new ArrayList<>();
    }

    public void save() {
        Arctic.getInstance().getLadderHandler().saveLadder(this);
    }

}