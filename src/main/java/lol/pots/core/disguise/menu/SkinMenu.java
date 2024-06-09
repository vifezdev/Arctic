package lol.pots.core.disguise.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.disguise.menu.button.SkinButton;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.pagination.PaginatedMenu;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SkinMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return CC.translate("Select a skin");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();

        int x = 1;
        int y = 0;

        for (String skin : Arctic.getInstance().getSettingsConfig().getStringList("DISGUISE.SKINS")) {
            buttonHashMap.put(getSlot(x++, y), new SkinButton(skin));

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
