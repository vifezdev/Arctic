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
public class GrantAddPacket extends Packet {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();
    private UUID target;
    private Grant grant;

    public GrantAddPacket(UUID target, Grant grant) {
        this.target = target;
        this.grant = grant;
    }

    @Override
    public void onReceive() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getGrants().add(grant);
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        profile.reload();
        if (grant.getAddedDuration() == Integer.MAX_VALUE) {
            Bukkit.getPlayer(target).sendMessage(Arctic.getInstance().getMsgConfig().getString("GRANT.PLAYER_PERM").replace("{rank}", grant.getRank().getColor()));
        } else {
            Bukkit.getPlayer(target).sendMessage(Arctic.getInstance().getMsgConfig().getString("GRANT.PLAYER_TEMP").replace("{rank}", grant.getRank().getColor()).replace("{duration}", grant.getAddedDurationText()));
        }
    }

    @Override
    public void onSend() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getGrants().add(grant);
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        profile.reload();
        if (grant.getAddedDuration() == Integer.MAX_VALUE) {
            //Bukkit.getPlayer(target).sendMessage(CC.translate("&aYou've been granted the " + grant.getRank().getColor() + grant.getAddedRank() + "&a rank for forever."));

        } else {
            Bukkit.getPlayer(target).sendMessage(CC.translate("&aYou've been granted the " + grant.getRank().getColor() + grant.getAddedRank() + "&a rank for " + grant.getAddedDurationText() + "&a."));
        }
    }

}
