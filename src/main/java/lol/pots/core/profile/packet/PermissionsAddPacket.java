package lol.pots.core.profile.packet;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.profile.Profile;
import lol.pots.core.redis.packet.Packet;

import java.util.UUID;

@Getter
@Setter
public class PermissionsAddPacket extends Packet {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private UUID target;
    private String permission;

    public PermissionsAddPacket(UUID target, String permission) {
        this.target = target;
        this.permission = permission;
    }

    @Override
    public void onReceive() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getIndividualPermissions().add(permission);
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        profile.recalculatePermissions();
        String message = "&7[&9Network Update&7] &fUpdated the profile &r" + profile.getActiveGrant().getRank().getColor() + profile.getUsername() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

    @Override
    public void onSend() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getIndividualPermissions().add(permission);
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        profile.recalculatePermissions();
        String message = "&7[&9Network Update&7] &fUpdated the profile &r" + profile.getActiveGrant().getRank().getColor() + profile.getUsername() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

}
