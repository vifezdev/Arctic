package lol.pots.core.punishment.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.Punishment;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.punishment.menu.button.PunishmentButton;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.pagination.PaginatedMenu;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
public class PunishmentTypeMenu extends PaginatedMenu {

    private Profile target;
    private boolean filtered;
    private PunishmentType type;

    @Override
    public boolean isOutline() {
        return true;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        List<Punishment> punishmentList;
        if (filtered) {
            punishmentList = target.getPunishmentsList(type);
        } else {
            punishmentList = target.getPunishments();
        }
        String context = punishmentList.size() > 1 ? "s" : "";
        if (type.getName().substring(type.getName().length() - 1).equalsIgnoreCase("h")) {
            context = "e" + context;
        }
        return CC.translate(target.getUsername() + "'s " + type.getName().toLowerCase() + context + " (" + punishmentList.size() + ")");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        List<Punishment> punishmentList;
        if (filtered) {
            punishmentList = target.getPunishmentsList(type);
        } else {
            punishmentList = target.getPunishments();
        }
        punishmentList.sort(Comparator.comparingInt(Punishment::getId));
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();

        int x = 1;
        int y = 0;

        for (Punishment punishment : punishmentList) {
            buttonHashMap.put(getSlot(x++, y), new PunishmentButton(target, punishment));

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
