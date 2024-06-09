package lol.pots.core.punishment.packet;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.profile.Profile;
import lol.pots.core.redis.packet.Packet;
import lol.pots.core.util.CC;

import java.util.UUID;

/**
 * @author hieu
 * @date 06/10/2023
 */

@Getter
@Setter
public class PunishmentClearPacket extends Packet {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private UUID target;

    public PunishmentClearPacket(UUID target) {
        this.target = target;
    }

    @Override
    public void onReceive() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getPunishments().clear();
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        Bukkit.getPlayer(target).sendMessage(CC.translate("&aYour punishments history has been cleared."));
    }

    @Override
    public void onSend() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getPunishments().clear();
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        Bukkit.getPlayer(target).sendMessage(CC.translate("&aYour punishments history has been cleared."));
    }

}
