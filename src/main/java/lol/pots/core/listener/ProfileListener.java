package lol.pots.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ConversationHandler;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.util.CC;
import lol.pots.core.util.TaskUtil;

import java.util.List;
import java.util.UUID;

import static lol.pots.core.values.Locale.*;

public class ProfileListener implements Listener {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();
    ConversationHandler conversationHandler = Arctic.getInstance().getConversationHandler();


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();
        Profile profile = new Profile(uuid);
        profile.setUsername(event.getPlayer().getName());
        profileHandler.getProfiles().add(profile);
        TaskUtil.runTaskLater( () -> {
            String kickMessage;
            if (profile.getActivePunishment(PunishmentType.BLACKLIST) != null) {
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BLACKLIST");
                event.getPlayer().kickPlayer( CC.translate(kickMessage.replace("{reason}", profile.getActivePunishment(PunishmentType.BLACKLIST).getAddedReason())));
                return;

            }

            profile.setLastSeenAddress(event.getPlayer().getAddress().getHostName());
            if (!profile.getIpAddresses().contains(event.getPlayer().getAddress().getHostName())) {
                profile.getIpAddresses().add(event.getPlayer().getAddress().getHostName());
            }
            profile.save();

            if (profile.getActivePunishment(PunishmentType.IP_BAN) != null) {
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BAN");
                event.getPlayer().kickPlayer( CC.translate(kickMessage
                        .replace("{issuer}", profile.getActivePunishment(PunishmentType.IP_BAN).getAddedBy())
                        .replace("{duration}", profile.getActivePunishment(PunishmentType.IP_BAN).getExpiresInDurationText())
                        .replace("{reason}", profile.getActivePunishment(PunishmentType.IP_BAN).getAddedReason())));
                return;
            }

            if (profile.getActivePunishment(PunishmentType.BAN) != null) {
                kickMessage = Arctic.getInstance().getMsgConfig().getString("PUNISHMENTS.BAN");
                event.getPlayer().kickPlayer( CC.translate(kickMessage
                        .replace("{issuer}", profile.getActivePunishment(PunishmentType.BAN).getAddedBy())
                        .replace("{duration}", profile.getActivePunishment(PunishmentType.BAN).getExpiresInDurationText())
                        .replace("{reason}", profile.getActivePunishment(PunishmentType.BAN).getAddedReason())));
                return;
            }



            if (profile.getStaffSettings().isVanished()) {
                Arctic.getInstance().getVanishHandler().vanishTarget(event.getPlayer());
            }

            profile.reload();
            List<Profile> alts = profileHandler.getProfilesByLastSeenAddress(profile.getLastSeenAddress());
            for (Profile profile1 : alts) {
                if (profile1.getActivePunishment(PunishmentType.IP_BAN) != null) {
                    Arctic.getInstance().getServer().dispatchCommand(Arctic.getInstance().getServer().getConsoleSender(), "ban " + profile.getUsername() + " perm Ban Evading: " + profile1.getUsername() + " -s");
                }
            }
            if (event.getPlayer().hasPermission("arctic.staff")) {
                if (profile.getLastSeenServer().equals("None")) {
                    String message = (String) STAFF_JOIN.getString();
                    assert message != null;
                    Arctic.getInstance().broadcastMessage(CC.translate(message
                            .replace("{staff}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                            .replace("{server}", Bukkit.getServerName())), "arctic.staff");
                } else {
                    String message = Arctic.getInstance().getSettingsConfig().getString("STAFF.SWITCH");
                    Arctic.getInstance().broadcastMessage(CC.translate(message
                            .replace("{server}", Bukkit.getServerName())
                            .replace("{staff}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                            .replace("{to}", Bukkit.getServerName())
                            .replace("{from}", profile.getLastSeenServer())), "arctic.staff");
                }
            }
            profile.setLastSeenOn(System.currentTimeMillis());
            profile.setLastSeenServer(Bukkit.getServerName());
            for (UUID friend : profile.getFriends()) {
                Profile friendProfiles = Arctic.getInstance().getProfileHandler().getProfileByUUID(friend);

                if (friendProfiles.getPlayer() != null) {
                    String friendJoin = FRIEND_PREFIX.getString() + FRIEND_JOIN.getString();

                    friendProfiles.getPlayer().sendMessage(CC.translate(friendJoin.replace("{player}", profile.getFancyUsername())));
                }
            }


            for (Player p : Bukkit.getOnlinePlayers()) {
                if (Arctic.getInstance().getProfileHandler().getProfileByUUID(p.getUniqueId()).getStaffSettings().isVanished()) {
                    Arctic.getInstance().getVanishHandler().hidePlayerFor(event.getPlayer(), p);
                }
            }


            profile.save();
            event.setJoinMessage(null);
        },0);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        Profile profile = profileHandler.getProfileByUUID(uuid);
        long lastSeenOn = System.currentTimeMillis();
        profile.setLastSeenOn(lastSeenOn);
        if (profile.isFrozen()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + profile.getUsername() + " 30d Logging out while frozen");
        }
        if (event.getPlayer().hasPermission("arctic.staff")) {
            TaskUtil.runTaskLater(() -> {
                Profile newProfile = Arctic.getInstance().getProfileHandler().getProfileByUUID(uuid);
                if (newProfile.getLastSeenOn() != lastSeenOn) return;
                newProfile.setLastSeenServer("None");
                newProfile.save();
                String message = Arctic.getInstance().getSettingsConfig().getString("STAFF.LEAVE");
                Arctic.getInstance().broadcastMessage(CC.translate(message
                        .replace("{staff}", profile.getActiveGrant().getRank().getColor() + profile.getUsername())
                        .replace("{server}", Bukkit.getServerName())), "arctic.staff");
            }, 60L);
        }
        for (UUID friend : profile.getFriends()) {
            Profile friendProfiles = Arctic.getInstance().getProfileHandler().getProfileByUUID(friend);

            if (friendProfiles.getPlayer() != null) {
                String friendLeave = FRIEND_PREFIX.getString() + FRIEND_LEAVE.getString();
                friendProfiles.getPlayer().sendMessage(CC.translate(friendLeave.replace("{player}", profile.getFancyUsername())));
            }
        }
        profile.save();
        conversationHandler.removeConversation(uuid);
        profileHandler.getProfiles().remove(profile);
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event) {
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(event.getPlayer().getUniqueId());
        profile.setLastSeenOn(System.currentTimeMillis());
        profile.setLastSeenServer("None");
        profile.save();
        conversationHandler.removeConversation(event.getPlayer().getUniqueId());
        profileHandler.getProfiles().remove(profile);
    }
}
