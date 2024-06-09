package lol.pots.core.note.menu.button;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.note.Note;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NoteButton extends Button {

    private Note note;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" &7▪ &fNote: &b" + note.getAddedReason());
        lore.add(" &7▪ &fAdded By: &r" + note.getAddedBy());
        lore.add("");

        String name = "&7[#" + note.getId() + "] &b" + note.getAddedOnText();
        return new ItemBuilder(Material.EMPTY_MAP).name(name).lore(lore).build();
    }

}
