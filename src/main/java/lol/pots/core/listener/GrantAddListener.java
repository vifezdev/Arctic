package lol.pots.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import lol.pots.core.Arctic;
import lol.pots.core.grant.packet.GrantAddPacket;
import lol.pots.core.procedure.Procedure;
import lol.pots.core.procedure.ProcedureStage;
import lol.pots.core.procedure.ProcedureType;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.TaskUtil;
import lol.pots.core.util.TimeUtil;
import lol.pots.core.util.menu.menus.ConfirmMenu;

public class GrantAddListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("arctic.grants.add")) return;
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        Procedure procedure = profile.getProcedure();
        if (procedure == null) return;
        event.setCancelled(true);
        if (procedure.getProcedureType() != ProcedureType.GRANT_ADD) return;
        if (procedure.getProcedureStage() == ProcedureStage.REQUIRE_DURATION) {
            long duration = TimeUtil.convertStringToLong(event.getMessage());
            if (event.getMessage().equalsIgnoreCase("cancel")) {
                player.sendMessage(CC.translate("&cGrant add procedure cancelled."));
                profile.setProcedure(null);
                return;
            }
            if (duration <= 0) {
                player.sendMessage(CC.translate("&cGrant add procedure unsuccessful. Invalid duration length."));
                profile.setProcedure(null);
                return;
            }
            procedure.setDuration(duration);
            procedure.setProcedureStage(ProcedureStage.REQUIRE_REASON);
            player.sendMessage(CC.translate("&fEnter a reason for adding this grant: &7(Type 'cancel' to cancel)"));
            return;
        }
        if (procedure.getProcedureStage() == ProcedureStage.REQUIRE_REASON) {
            procedure.setReason(event.getMessage());
            if (event.getMessage().equalsIgnoreCase("cancel")) {
                player.sendMessage(CC.translate("&cGrant add procedure cancelled."));
                profile.setProcedure(null);
                return;
            }
            new ConfirmMenu(CC.translate("Confirm grant"), data -> {
                if (data) {
                    procedure.getGrant().setAddedDuration(procedure.getDuration());
                    procedure.getGrant().setAddedReason(procedure.getReason());
                    GrantAddPacket packet = new GrantAddPacket(procedure.getTarget(), procedure.getGrant());
                    Arctic.getInstance().getRedisHandler().sendPacket(packet);
                    player.sendMessage(CC.translate("&aGrant pardon procedure successful."));
                } else {
                    player.sendMessage(CC.translate("&cGrant pardon procedure unsuccessful."));
                }
                profile.setProcedure(null);
                TaskUtil.runTaskLater(player::closeInventory, 1L);
            }).openMenu(player);
        }
    }

}
