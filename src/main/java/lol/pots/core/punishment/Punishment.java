package lol.pots.core.punishment;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.util.DateUtil;
import lol.pots.core.util.TimeUtil;

@Getter
@Setter
public class Punishment {

    private int id;
    private String addedPunishment, addedBy, addedReason, pardonedBy, pardonedReason;
    private long addedOn, addedDuration, pardonedOn;
    private boolean addedSilent, pardoned, pardonedSilent;

    public Punishment(int id, String addedPunishment, String addedBy, long addedOn, long addedDuration, String addedReason, boolean addedSilent, boolean pardoned, String pardonedBy, String pardonedReason, long pardonedOn, boolean pardonedSilent) {
        this.id = id;
        this.addedPunishment = addedPunishment;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.addedDuration = addedDuration;
        this.addedReason = addedReason;
        this.addedSilent = addedSilent;
        this.pardoned = pardoned;
        this.pardonedBy = pardonedBy;
        this.pardonedReason = pardonedReason;
        this.pardonedOn = pardonedOn;
        this.pardonedSilent = pardonedSilent;
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

    public PunishmentType getPunishmentType() {
        return PunishmentType.getPunishmentTypeByName(addedPunishment);
    }

}
