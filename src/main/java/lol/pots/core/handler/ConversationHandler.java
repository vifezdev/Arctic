package lol.pots.core.handler;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class ConversationHandler {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();
    private HashMap<UUID, UUID> conversations;

    public ConversationHandler() {
        conversations = new HashMap<>();
    }

    public void sendMessage(UUID sender, UUID target, String message) {
        conversations.put(sender, target);
        conversations.put(target, sender);

        Player senderPlayer = Bukkit.getPlayer(sender);
        Player targetPlayer = Bukkit.getPlayer(target);
        String format;

        Profile senderProfile = profileHandler.getProfileByUUID(sender);
        Profile targetProfile = profileHandler.getProfileByUUID(target);

        if (!senderProfile.getSettings().isPrivateMessages()) {
            senderPlayer.sendMessage(CC.translate("&cYou don't have your private messages enabled."));
            return;
        }

        if (!targetProfile.getSettings().isPrivateMessages()) {
            senderPlayer.sendMessage(CC.translate("&cThat player doesn't have their private messages enabled."));
            return;
        }

        if (senderProfile.getIgnored().contains(target)) {
            senderPlayer.sendMessage(CC.translate("&cYou're ignoring that player."));
            return;
        }

        if (targetProfile.getIgnored().contains(sender)) {
            senderPlayer.sendMessage(CC.translate("&cThat player's ignoring you."));
            return;
        }

        format = Arctic.getInstance().getSettingsConfig().getString("CONVERSATIONS.TO");
        senderPlayer.sendMessage(CC.translate(format
                .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + targetPlayer.getName())
                .replace("{message}", ChatColor.stripColor(message))));

        format = Arctic.getInstance().getSettingsConfig().getString("CONVERSATIONS.FROM");
        targetPlayer.sendMessage(CC.translate(format
                .replace("{sender}", senderProfile.getActiveGrant().getRank().getColor() + senderPlayer.getName())
                .replace("{message}", ChatColor.stripColor(message))));

        if (senderProfile.getSettings().isPrivateMessagingSounds()) {
            senderPlayer.playSound(Bukkit.getPlayer(sender).getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
        }

        if (targetProfile.getSettings().isPrivateMessagingSounds()) {
            targetPlayer.playSound(Bukkit.getPlayer(target).getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
        }
    }

    public void removeConversation(UUID uuid) {
        if (conversations.isEmpty()) return;
        for (UUID uuid1 : conversations.keySet()) {
            if (uuid1.equals(uuid)) conversations.remove(uuid);
        }
        for (UUID uuid1 : conversations.values()) {
            if (uuid1.equals(uuid)) conversations.values().remove(uuid);
        }
    }

}
