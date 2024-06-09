package lol.pots.core.rank.menu;

import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.menu.button.RankEditorButton;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.pagination.PaginatedMenu;

import java.util.HashMap;
import java.util.Map;
public class RankEditorMenu extends PaginatedMenu {

    @Override
    public boolean isOutline() {
        return true;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return CC.translate("Select a rank");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();

        int x = 1;
        int y = 0;

        for (Rank rank : Arctic.getInstance().getRankHandler().getRanks()) {
            buttonHashMap.put(getSlot(x++, y), new RankEditorButton(rank));

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
