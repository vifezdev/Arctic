package lol.pots.core.grant.packet;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import lol.pots.core.Arctic;
import lol.pots.core.grant.Grant;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.profile.Profile;
import lol.pots.core.redis.packet.Packet;
import lol.pots.core.util.CC;

import java.util.UUID;

@Getter
@Setter
public class GrantPardonPacket extends Packet {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private UUID target;
    private Grant grant;
    private String pardonedBy;
    private String pardonedReason;
    private long pardonedOn;

    public GrantPardonPacket(UUID target, Grant grant, String pardonedBy, String pardonedReason, long pardonedOn) {
        this.target = target;
        this.grant = grant;
        this.pardonedBy = pardonedBy;
        this.pardonedReason = pardonedReason;
        this.pardonedOn = pardonedOn;
    }

    @Override
    public void onReceive() {
        Profile profile = profileHandler.getProfileByUUID(target);
        for (Grant grant1 : profile.getGrants()) {
            if (grant1.getAddedOn() == grant.getAddedOn()) {
                profile.getGrants().remove(grant1);
            }
        }
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        profile.reload();
        Bukkit.getPlayer(target).sendMessage(CC.translate("&aYour &r" + grant.getRank().getColor() + grant.getRank().getName() + " &ahas been removed."));
    }

    @Override
    public void onSend() {
        Profile profile = profileHandler.getProfileByUUID(target);
        for (Grant grant1 : profile.getGrants()) {
            if (grant1.getAddedOn() == grant.getAddedOn()) {
                grant1.setPardoned(true);
                grant1.setPardonedBy(pardonedBy);
                grant1.setPardonedReason(pardonedReason);
                grant1.setPardonedOn(pardonedOn);
            }
        }
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        profile.reload();
        Bukkit.getPlayer(target).sendMessage(CC.translate("&aYour &r" + grant.getRank().getColor() + grant.getRank().getName() + " &ahas been removed."));
    }

}
