package lol.pots.core.grant;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.Arctic;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.DateUtil;
import lol.pots.core.util.TimeUtil;

@Getter
@Setter
public class Grant {

    private int id;
    private String addedRank, addedBy, addedReason, pardonedBy, pardonedReason;
    private long addedOn, addedDuration, pardonedOn;
    private boolean pardoned;

    public Grant(int id, String addedRank, String addedBy, long addedOn, long addedDuration, String addedReason, boolean pardoned, String pardonedBy, String pardonedReason, long pardonedOn) {
        this.id = id;
        this.addedRank = addedRank;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.addedDuration = addedDuration;
        this.addedReason = addedReason;
        this.pardoned = pardoned;
        this.pardonedBy = pardonedBy;
        this.pardonedReason = pardonedReason;
        this.pardonedOn = pardonedOn;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= addedOn + addedDuration;
    }

    public String getAddedDurationText() {
        if (addedDuration == Integer.MAX_VALUE) return "Permanent";
        if (addedDuration == Integer.MIN_VALUE) return "Temporary";
        return TimeUtil.convertLongToString(addedDuration);
    }

    public String getExpiresInDurationText() {
        if (addedDuration == Integer.MAX_VALUE) return "forever";
        return TimeUtil.convertLongToString((addedOn + addedDuration) - System.currentTimeMillis());
    }

    public String getExpiredDateText() {
        return DateUtil.formatDate(addedOn + addedDuration);
    }

    public String getAddedOnText() {
        return DateUtil.formatDate(addedOn);
    }

    public String getPardonedOnText() {
        return DateUtil.formatDate(pardonedOn);
    }

    public Rank getRank() {
        return Arctic.getInstance().getRankHandler().getRankByName(addedRank);
    }

}
