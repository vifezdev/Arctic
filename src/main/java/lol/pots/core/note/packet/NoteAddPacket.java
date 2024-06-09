package lol.pots.core.note.packet;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.note.Note;
import lol.pots.core.profile.Profile;
import lol.pots.core.redis.packet.Packet;

import java.util.UUID;


@Getter
@Setter
public class NoteAddPacket extends Packet {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private UUID sender;
    private UUID target;
    private Note note;

    public NoteAddPacket(UUID target, UUID sender, Note note) {
        this.target = target;
        this.sender = sender;
        this.note = note;
    }

    @Override
    public void onReceive() {
        Profile profile = profileHandler.getProfileByUUID(target);
        Profile senderProfile = profileHandler.getProfileByUUID(sender);

        profile.getNotes().add(note);
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        String message = "&9[Staff] &7[" + Bukkit.getServerName() + "&7] ";
        Arctic.getInstance().broadcastMessage(message, "*");
    }//send it

    @Override
    public void onSend() {
        Profile profile = profileHandler.getProfileByUUID(target);
        profile.getNotes().add(note);
        profile.save();
        if (Bukkit.getPlayer(target) == null) return;
        profile.recalculatePermissions();
        String message = "&7[&9Network Update&7] &fUpdated the profile &r" + profile.getActiveGrant().getRank().getColor() + profile.getUsername() + "&f.";
        Arctic.getInstance().broadcastMessage(message, "*");
    }

}
