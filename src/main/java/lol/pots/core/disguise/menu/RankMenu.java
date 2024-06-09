package lol.pots.core.disguise.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.disguise.menu.button.RankButton;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.pagination.PageButton;
import lol.pots.core.util.menu.pagination.PaginatedMenu;

import java.util.*;

@AllArgsConstructor
public class RankMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return CC.translate("Select a rank");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();
        List<Rank> ranks = Arctic.getInstance().getRankHandler().getRanks();
        ranks.sort(Comparator.comparingInt(Rank::getWeight));
        Collections.reverse(ranks);

        int x = 1;
        int y = 0;

        buttonHashMap.put(0, new PageButton(-1, this));
        buttonHashMap.put(8, new PageButton(1, this));

        for (Rank rank : ranks) {
            if (rank.isDisguisable()) {
                buttonHashMap.put(getSlot(x++, y), new RankButton(rank));

                if (x == 8) {
                    y++;
                    x = 1;
                }
            }
        }

        return buttonHashMap;
    }

    @Override
    public boolean isClosedByMenu() {
        return false;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9 * 1;
    }

    @Override
    public int getSize() {
        return 9 * 3;
    }

    @Override
    public boolean isPlaceholder() {
        return true;
    }

}
