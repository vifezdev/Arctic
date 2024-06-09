package lol.pots.core.util.menu.pagination;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;

import java.util.Arrays;

@AllArgsConstructor
public class PageButton extends Button {

    private int mod;
    private PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        if (this.mod > 0) {
            if (hasNext(player)) {
                return new ItemBuilder(Material.PAPER)
                        .name("&bNext Page")
                        .lore(Arrays.asList(
                                "&fClick here to switch",
                                "&fto the next page"
                        ))
                        .build();
            } else {
                return new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .durability(7)
                        .name(" ")
                        .build();
            }
        } else {
            if (hasPrevious(player)) {
                return new ItemBuilder(Material.PAPER)
                        .name("&bPrevious Page")
                        .lore(Arrays.asList(
                                "&fClick here to switch ",
                                "&fto the previous page"
                        ))
                        .build();
            } else {
                return new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .durability(7)
                        .name(" ")
                        .build();
            }
        }
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (this.mod > 0) {
            if (hasNext(player)) {
                this.menu.modPage(player, this.mod);
            }
        } else {
            if (hasPrevious(player)) {
                this.menu.modPage(player, this.mod);
            }
        }
    }

    private boolean hasNext(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return this.menu.getPages(player) >= pg;
    }

    private boolean hasPrevious(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return pg > 0;
    }

}