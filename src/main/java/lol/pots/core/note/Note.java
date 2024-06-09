package lol.pots.core.note;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.util.DateUtil;

@Getter
@Setter
public class Note {

    private int id;
    private String addedBy, addedReason;
    private long addedOn;

    public Note(int id, String addedBy, long addedOn, String addedReason) {
        this.id = id;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.addedReason = addedReason;
    }

    public String getAddedOnText() {
        return DateUtil.formatDate(addedOn);
    }

}
