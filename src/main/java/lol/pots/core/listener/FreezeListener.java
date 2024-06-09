package lol.pots.core.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;

public class FreezeListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (profile.isFrozen()) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
                player.teleport(from);
                event.setCancelled(true);
            }
        }
    }
}