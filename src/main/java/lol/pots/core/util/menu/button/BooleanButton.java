package lol.pots.core.util.menu.button;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.util.CC;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.callback.Callback;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class BooleanButton extends Button {

    private final boolean confirm;
    private final Callback<Boolean> callback;

    @ConstructorProperties(value = {"confirm", "callback"})
    public BooleanButton(boolean confirm, Callback<Boolean> callback) {
        this.confirm = confirm;
        this.callback = callback;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        if (this.confirm) {
            lore.add(CC.translate("&aClick here to confirm"));
            lore.add(CC.translate("&athe procedure action."));
        } else {
            lore.add(CC.translate("&cClick here to cancel"));
            lore.add(CC.translate("&cthe procedure action."));
        }
        return new ItemBuilder(Material.WOOL).name(this.confirm ? CC.translate("&a&lConfirm") : CC.translate("&c&lCancel")).lore(lore).durability(this.confirm ? (byte) 5 : 14).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (this.confirm) {
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20.0f, 0.1f);
        } else {
            player.playSound(player.getLocation(), Sound.DIG_GRAVEL, 20.0f, 0.1f);
        }
        player.closeInventory();
        this.callback.callback(this.confirm);
    }
}

