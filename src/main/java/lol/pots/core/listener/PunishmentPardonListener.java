package lol.pots.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import lol.pots.core.Arctic;
import lol.pots.core.procedure.Procedure;
import lol.pots.core.procedure.ProcedureStage;
import lol.pots.core.procedure.ProcedureType;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.packet.PunishmentPardonPacket;
import lol.pots.core.util.CC;
import lol.pots.core.util.TaskUtil;
import lol.pots.core.util.menu.menus.ConfirmMenu;


public class PunishmentPardonListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("arctic.punishments.pardon")) return;
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        Procedure procedure = profile.getProcedure();
        if (procedure == null) return;
        event.setCancelled(true);
        if (procedure.getProcedureType() != ProcedureType.PUNISHMENT_PARDON) return;
        if (procedure.getProcedureStage() == ProcedureStage.REQUIRE_REASON) {
            procedure.setReason(event.getMessage());
            if (event.getMessage().equalsIgnoreCase("cancel")) {
                player.sendMessage(CC.translate("&cPunishment pardon procedure was cancelled."));
                profile.setProcedure(null);
                return;
            }
            new ConfirmMenu(CC.translate("Confirm pardon"), data -> {
                if (data) {
                    String pardonedBy = profile.getActiveGrant().getRank().getColor() + profile.getUsername();
                    long pardonedOn = System.currentTimeMillis();
                    PunishmentPardonPacket packet = new PunishmentPardonPacket(procedure.getTarget(), procedure.getPunishment(), pardonedBy, event.getMessage(), pardonedOn, false);
                    Arctic.getInstance().getRedisHandler().sendPacket(packet);
                } else {
                    player.sendMessage(CC.translate("&cPunishment pardon procedure was unsuccessful."));
                }
                profile.setProcedure(null);
                TaskUtil.runTaskLater(player::closeInventory, 1L);
            }).openMenu(player);
        }
    }

}
