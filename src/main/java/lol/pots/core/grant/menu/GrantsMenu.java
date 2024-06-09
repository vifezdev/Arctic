package lol.pots.core.grant.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import lol.pots.core.grant.Grant;
import lol.pots.core.grant.menu.button.GrantsButton;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.pagination.PaginatedMenu;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class GrantsMenu extends PaginatedMenu {

    private Profile profile;

    @Override
    public boolean isOutline() {
        return true;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return CC.translate(profile.getUsername() + "'s grants (" + profile.getGrants().size() + ")");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();
        profile.getGrants().sort(Comparator.comparingInt(Grant::getId));

        int x = 1;
        int y = 0;

        for (Grant grant : profile.getGrants()) {
            buttonHashMap.put(getSlot(x++, y), new GrantsButton(profile, grant));

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
