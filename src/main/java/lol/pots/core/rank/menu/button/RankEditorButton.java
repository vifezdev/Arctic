package lol.pots.core.rank.menu.button;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.CC;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RankEditorButton extends Button {

    private Rank rank;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" &7▪ &fName: " + rank.getName());
        lore.add(" &7▪ &fWeight: &b" + rank.getWeight());
        lore.add(" &7▪ &fPrefix: " + rank.getPrefix());
        lore.add(" &7▪ &fSuffix: " + rank.getPrefix());
        lore.add("");
        lore.add(" &aClick to edit!");
        return new ItemBuilder(Material.WOOL).name(rank.getColor() + rank.getName()).lore(lore).durability(rank.getDurability()).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (!player.hasPermission("arctic.rank.editor")) {
            playFail(player);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        player.sendMessage(CC.translate("&cComing soon!"));
        playNeutral(player);
    }
}
