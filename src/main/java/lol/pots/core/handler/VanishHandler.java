package lol.pots.core.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;

public class VanishHandler {

    private final String vanishPriorityPermission = "core.vanish.priority.";

    public int getVanishPriority(Player p) {
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(p.getUniqueId());

        int highestPriority = Integer.MIN_VALUE;

        for (String perm: profile.getTotalPermissions()){
            if(perm.contains(vanishPriorityPermission)) {
                int tmp = Integer.parseInt(perm.substring(vanishPriorityPermission.length()));
                if (tmp > highestPriority) {
                    highestPriority = tmp;
                }
            }

        }
        return highestPriority;

    }

    public void vanishTarget(Player target){
        for (Player p : Bukkit.getOnlinePlayers()){
            if(getVanishPriority(p) > getVanishPriority(target))
                return;
            p.hidePlayer(target);
        }

        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());
        profile.getStaffSettings().setVanished(true);
        profile.save();

    }

    public void unvanishTarget(Player target){
        for (Player p: Bukkit.getOnlinePlayers()){
            p.showPlayer(target);
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());
        profile.getStaffSettings().setVanished(false);
        profile.save();
    }

    public void hidePlayerFor(Player player, Player target){
        if (getVanishPriority(target) > getVanishPriority(player))
            return;
        target.hidePlayer(player);
    }

}
