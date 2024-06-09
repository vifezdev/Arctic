package lol.pots.core.disguise.menu.button;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;

@AllArgsConstructor
public class RankButton extends Button {

    private Rank rank;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.WOOL).name(rank.getColor() + rank.getName()).durability(rank.getDurability()).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());

        if (!player.hasPermission("arctic.disguise")) {
            playFail(player);
            return;
        }

        player.closeInventory();

        profile.getDisguiseProcedure().setRank(rank);
        profile.getDisguiseProcedure().send();

        playNeutral(player);
    }

}
