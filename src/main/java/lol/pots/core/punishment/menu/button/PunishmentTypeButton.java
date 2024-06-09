package lol.pots.core.punishment.menu.button;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.Punishment;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.punishment.menu.PunishmentTypeMenu;
import lol.pots.core.util.CC;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hieu
 * @date 06/10/2023
 */

@AllArgsConstructor
public class PunishmentTypeButton extends Button {

    private Profile target;
    private boolean filtered;
    private PunishmentType type;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        List<Punishment> punishments;
        if (filtered) {
            punishments = target.getPunishmentsList(type);
        } else {
            punishments = target.getPunishments();
        }
        lore.add("");
        lore.add(CC.translate(" &aClick to view!"));
        int amount = punishments.isEmpty() ? 1 : punishments.size();
        return new ItemBuilder(Material.WOOL).name(type.getColor() + type.getName() + " &7(" + target.getPunishmentsList(type).size() + ")").lore(lore).durability(type.getDurability()).amount(amount).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        List<Punishment> punishments;
        if (filtered) {
            punishments = target.getPunishmentsList(type);
        } else {
            punishments = target.getPunishments();
        }
        if (punishments.isEmpty()) {
            playFail(player);
            player.sendMessage(CC.translate("&c" + target.getUsername() + " has no " + type.getName().toLowerCase() + " data"));
            return;
        }
        new PunishmentTypeMenu(target, filtered, type).openMenu(player);
        playNeutral(player);
    }

}
