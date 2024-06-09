package lol.pots.core.rank.packet;

import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.redis.packet.Packet;
import lol.pots.core.util.CC;

import static lol.pots.core.values.Locale.*;

public class PlayerVanishPacket extends Packet {

    private Player toVanish;

    private boolean isSelf;

    Profile profile;


    public PlayerVanishPacket(Player toVanish, Profile profile, boolean isSelf) {
        this.toVanish = toVanish;
        this.isSelf = isSelf;
        this.profile = profile;

    }

    @Override
    public void onReceive() {
        if (profile.getStaffSettings().isVanished()) {
            Arctic.getInstance().getVanishHandler().unvanishTarget(toVanish);
            if (isSelf) {
                toVanish.sendMessage(CC.translate(SELF_UNVANISH.getString()));
            }

            String message = CC.translate(OTHER_UNVANISH.getString().replace("{profile}", profile.getFancyUsername()));
            Arctic.getInstance().broadcastMessage(message, "arctic.staffalerts", toVanish);


        } else {
            Arctic.getInstance().getVanishHandler().vanishTarget(toVanish);
            if (isSelf) {
                toVanish.sendMessage(CC.translate(SELF_VANISH.getString()));
            }

            String message = CC.translate(OTHER_VANISH.getString().replace("{profile}", profile.getFancyUsername()));
            Arctic.getInstance().broadcastMessage(message, "arctic.staffalerts", toVanish);
        }
    }

    @Override
    public void onSend() {
        if (profile.getStaffSettings().isVanished()) {
            Arctic.getInstance().getVanishHandler().unvanishTarget(toVanish);
            if (isSelf) {
                toVanish.sendMessage(CC.translate(SELF_UNVANISH.getString()));
            }

            String message = CC.translate(OTHER_UNVANISH.getString().replace("{profile}", profile.getFancyUsername()));
            Arctic.getInstance().broadcastMessage(message, "arctic.staffalerts", toVanish);


        } else {
            Arctic.getInstance().getVanishHandler().vanishTarget(toVanish);
            if (isSelf) {
                toVanish.sendMessage(CC.translate(SELF_VANISH.getString()));
            }

            String message = CC.translate(OTHER_VANISH.getString().replace("{profile}", profile.getFancyUsername()));
            Arctic.getInstance().broadcastMessage(message, "arctic.staffalerts", toVanish);
        }
    }
}

