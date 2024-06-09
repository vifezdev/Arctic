package lol.pots.core.tags.menu.button;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.profile.Profile;
import lol.pots.core.tags.Tag;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;

public class PrefixButton extends Button {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private Tag tag;

    public PrefixButton(Tag tag) {
        this.tag = tag;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        Profile profile = profileHandler.getProfileByUUID(player.getUniqueId());
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" &fShows as:");
        lore.add("  &7▪ " + profile.getDisplayName() + " " + tag.getPrefix());
        lore.add("");
        if (player.hasPermission("arctic.prefix." + tag.getName().toLowerCase())) {
            if (profile.getTag() == tag) {
                lore.add(" &cClick to deselect!");
            } else {
                lore.add(" &aClick to select!");
            }
        } else {
            lore.add(" &cYou don't own this tag!");
        }
        return new ItemBuilder(Material.NAME_TAG).name("&b" + tag.getDisplayName()).lore(lore).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (player.hasPermission("arctic.prefix." + tag.getName().toLowerCase())) {
            Profile profile = profileHandler.getProfileByUUID(player.getUniqueId());
            if (profile.getTag() == tag) {
                profile.setTag(null);
            } else {
                profile.setTag(tag);
            }
            profile.reload();
            playNeutral(player);
        } else {
            playFail(player);
        }
    }

}
