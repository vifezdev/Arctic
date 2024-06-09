package lol.pots.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import lol.pots.core.Arctic;
import lol.pots.core.packet.BroadcastStaffMessagePacket;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.util.CC;

import static lol.pots.core.values.Locale.SELF_FILTER;


public class ChatListener implements Listener {

    PunishmentType type = PunishmentType.MUTE;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(event.getPlayer().getUniqueId());
        if (profile.getActivePunishment(type) != null) {
            event.getPlayer().sendMessage(CC.translate("&cYou're muted for &e{duration}&c.".replace("{duration}", profile.getActivePunishment(type).getExpiresInDurationText())));
            event.setCancelled(true);
            return;
        }

        if (Arctic.getInstance().getFilterManagement().shouldFilterMessage(event.getMessage())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(CC.translate(SELF_FILTER.getString()));
            return;
        }

        if (!profile.getSettings().isGlobalChat()) {
            event.setCancelled(true);
            event.getRecipients().remove(event.getPlayer());
            return;
        }
        String message;
        if (profile.getStaffSettings().isStaffChat()) {
            event.setCancelled(true);
            message = Arctic.getInstance().getMsgConfig().getString("CHAT-FORMAT.STAFF");
            BroadcastStaffMessagePacket packet = new BroadcastStaffMessagePacket(message
                    .replace("{server}", Bukkit.getServerName())
                    .replace("{staff}", profile.getActiveGrant().getRank().getColor() + event.getPlayer().getName())
                    .replace("{message}", escapePercent(event.getMessage())));
            Arctic.getInstance().getRedisHandler().sendPacket(packet);
            return;
        }
        message = Arctic.getInstance().getSettingsConfig().getString("CHAT-FORMAT.DEFAULT");
        event.setFormat(CC.translate(message
                .replace("{profile}", profile.getDisguiseProfile() == null ? event.getPlayer().getPlayer().getDisplayName() : profile.getDisguiseProfile().getRank().getPrefix() + profile.getDisguiseProfile().getName())
                .replace("{tag}", (profile.getTag() == null ? "" : profile.getTag().getPrefix()) + "&r")
                .replace("{message}", escapePercent(event.getMessage()))));
    }



    private String escapePercent(String input) {
        return input.replace("%", "%%");
    }
}
