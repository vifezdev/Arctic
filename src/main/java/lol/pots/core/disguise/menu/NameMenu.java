package lol.pots.core.disguise.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.disguise.menu.button.NameButton;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.pagination.PageButton;
import lol.pots.core.util.menu.pagination.PaginatedMenu;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class NameMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return CC.translate("Select a name");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();

        int x = 1;
        int y = 0;

        buttonHashMap.put(0, new PageButton(-1, this));
        buttonHashMap.put(8, new PageButton(1, this));

        for (String name : Arctic.getInstance().getSettingsConfig().getStringList("DISGUISE.NAMES")) {
            buttonHashMap.put(getSlot(x++, y), new NameButton(name));

            if (x == 8) {
                y++;
                x = 1;
            }
        }

        return buttonHashMap;
    }

    @Override
    public boolean isClosedByMenu() {
        return true;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9 * 2;
    }

    @Override
    public int getSize() {
        return 9 * 4;
    }

    @Override
    public boolean isPlaceholder() {
        return true;
    }

}
