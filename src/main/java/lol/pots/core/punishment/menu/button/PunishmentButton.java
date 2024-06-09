package lol.pots.core.punishment.menu.button;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.procedure.Procedure;
import lol.pots.core.procedure.ProcedureStage;
import lol.pots.core.procedure.ProcedureType;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.Punishment;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.util.CC;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.TaskUtil;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hieu
 * @date 06/10/2023
 */

@AllArgsConstructor
public class PunishmentButton extends Button {

    private Profile target;
    private Punishment punishment;

    @Override
    public ItemStack getButtonItem(Player player) {
        int durability = 0;
        String context;
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" &7▪ &fPunishment Type: &b" + (punishment.getPunishmentType().getColor() + punishment.getPunishmentType().getName()));
        lore.add(" &7▪ &fAdded By: &b" + punishment.getAddedBy());
        if (punishment.getPunishmentType() != PunishmentType.WARN && punishment.getPunishmentType() != PunishmentType.KICK) {
            lore.add(" &7▪ &fDuration: &b" + punishment.getAddedDurationText());
        }
        lore.add(" &7▪ &fReason: &b" + punishment.getAddedReason());
        if (!punishment.isExpired() && !punishment.isPardoned()) {
            lore.add(" &7▪ &fExpires In: &b" + punishment.getExpiresInDurationText());
        }
        context = punishment.isAddedSilent() ? "&aTrue" : "&cFalse";
        lore.add(" &7▪ &fPunished Silently: &r" + context);
        if (!punishment.isExpired() && !punishment.isPardoned()) {
            lore.add(" &aClick to pardon punishment!");
            durability = 5;
        } else if (punishment.isExpired() && punishment.getPunishmentType() != PunishmentType.WARN && punishment.getPunishmentType() != PunishmentType.KICK) {
            lore.add("");
            lore.add(" &bPunishment Expired:");
            lore.add("  &7▪ &fExpired On: &b" + punishment.getExpiredDateText());
            durability = 1;
        } else if (punishment.isPardoned()) {
            lore.add("");
            lore.add(" &bPunishment Pardoned:");
            lore.add("  &7▪ &fPardoned By: &b" + punishment.getPardonedBy());
            lore.add("  &7▪ &fPardoned On: &b" + punishment.getPardonedOnText());
            lore.add("  &7▪ &fReason: &b" + punishment.getPardonedReason());
            context = punishment.isPardoned() ? "&aTrue" : "&cFalse";
            lore.add("  &7▪ &fPardoned Silently: &r" + context);
            durability = 14;
        }
        String name = "&7[#" + punishment.getId() + "] &b" + punishment.getAddedOnText();
        return new ItemBuilder(Material.STAINED_GLASS_PANE).name(name).lore(lore).durability(durability).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (!player.hasPermission("arctic.punishments.pardon")) {
            playFail(player);
            return;
        }
        if (punishment.isExpired() || punishment.isPardoned()) {
            playFail(player);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        profile.setProcedure(new Procedure(ProcedureType.PUNISHMENT_PARDON, ProcedureStage.REQUIRE_REASON, target.getUuid(), null, punishment, "", System.currentTimeMillis()));
        TaskUtil.runTaskLater(player::closeInventory, 1L);
        player.sendMessage(CC.translate("&fEnter a reason for pardoning this punishment: &7(Type 'cancel' to cancel)"));
        playNeutral(player);
    }

}
