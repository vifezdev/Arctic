package lol.pots.core.grant.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.grant.menu.button.GrantButton;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.pagination.PageButton;
import lol.pots.core.util.menu.pagination.PaginatedMenu;

import java.util.*;

@AllArgsConstructor
public class GrantMenu extends PaginatedMenu {

    private UUID target;

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
        List<Rank> ranks = Arctic.getInstance().getRankHandler().getRanks();
        ranks.sort(Comparator.comparingInt(Rank::getWeight));
        Collections.reverse(ranks);
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());

        int x = 1;
        int y = 0;

        buttonHashMap.put(0, new PageButton(-1, this));
        buttonHashMap.put(8, new PageButton(1, this));

        for (Rank rank : ranks) {
            if (profile.getActiveGrant().getRank().getWeight() > rank.getWeight()) {
                buttonHashMap.put(getSlot(x++, y), new GrantButton(target, rank));

                if (x == 8) {
                    y++;
                    x = 1;
                }
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
