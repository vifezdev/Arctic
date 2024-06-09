package lol.pots.core.disguise.menu.button;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.disguise.menu.RankMenu;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SkinButton extends Button {

    private String skin;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" &aClick to select!");
        return new ItemBuilder(Material.SKULL_ITEM).name("&a" + skin).durability(3).owner(skin).lore(lore).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (!player.hasPermission("arctic.disguise")) {
            playFail(player);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());

        profile.getDisguiseProcedure().setSkin(skin);
        new RankMenu().openMenu(player);
    }
}
