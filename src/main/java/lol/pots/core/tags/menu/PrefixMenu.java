package lol.pots.core.tags.menu;

import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.tags.Tag;
import lol.pots.core.tags.menu.button.PrefixButton;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.pagination.PageButton;
import lol.pots.core.util.menu.pagination.PaginatedMenu;

import java.util.HashMap;
import java.util.Map;

public class PrefixMenu extends PaginatedMenu {

    @Override
    public boolean isOutline() {
        return true;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return CC.translate("Select a tag");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();

        int x = 1;
        int y = 0;

        buttonHashMap.put(0, new PageButton(-1, this));
        buttonHashMap.put(8, new PageButton(1, this));

        for (Tag tag : Arctic.getInstance().getTagHandler().getTags()) {
            buttonHashMap.put(getSlot(x++, y), new PrefixButton(tag));

            if (x == 8) {
                y++;
                x = 1;
            }
        }
        return buttonHashMap;

    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9 * 2;
    }

    @Override
    public int getSize() {
        return 9 * 4;
    }
}
