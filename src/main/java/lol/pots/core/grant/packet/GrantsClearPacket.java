package lol.pots.core.grant.packet;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import lol.pots.core.Arctic;
import lol.pots.core.grant.Grant;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.redis.packet.Packet;
import lol.pots.core.util.CC;

import java.util.UUID;

@Getter
@Setter
public class GrantsClearPacket extends Packet {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private UUID target;

    public GrantsClearPacket(UUID target) {
        this.target = target;
    }

    @Override
    public void onReceive() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getGrants().clear();
        Rank rank = Arctic.getInstance().getRankHandler().getDefaultRank();
        long addedOn = System.currentTimeMillis();
        Grant grant = new Grant(profile.getGrants().size() + 1, rank.getName(), "&4Console", addedOn, Integer.MAX_VALUE, "Console Grant", false, "", "", addedOn);
        profile.getGrants().add(grant);
        if (Bukkit.getPlayer(target) == null) return;
        profile.reload();
        Bukkit.getPlayer(target).sendMessage(CC.translate("&aYour grants history has been cleared."));
    }

    @Override
    public void onSend() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getGrants().clear();
        Rank rank = Arctic.getInstance().getRankHandler().getDefaultRank();
        long addedOn = System.currentTimeMillis();
        Grant grant = new Grant(profile.getGrants().size() + 1, rank.getName(), "&4Console", addedOn, Integer.MAX_VALUE, "Console Grant", false, "", "", addedOn);
        profile.getGrants().add(grant);
        if (Bukkit.getPlayer(target) == null) return;
        profile.reload();
        Bukkit.getPlayer(target).sendMessage(CC.translate("&aYour grants history has been cleared."));
    }

}
