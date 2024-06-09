package lol.pots.core.disguise.menu.button;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.disguise.menu.SkinMenu;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NameButton extends Button {

    private String name;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" &aClick to select!");
        return new ItemBuilder(Material.NAME_TAG).name("&a" + name).lore(lore).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (!player.hasPermission("arctic.disguise")) {
            playFail(player);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());

        profile.getDisguiseProcedure().setName(name);
        new SkinMenu().openMenu(player);
    }
}
