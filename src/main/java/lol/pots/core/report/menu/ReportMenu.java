package lol.pots.core.report.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import lol.pots.core.report.ReportType;
import lol.pots.core.report.menu.button.ReportButton;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class ReportMenu extends Menu {

    private UUID target;

    @Override
    public boolean isOutline() {
        return true;
    }

    @Override
    public String getTitle(Player player) {
        return CC.translate("Select a reason");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();

        int x = 1;
        int y = 1;

        for (ReportType type : ReportType.values()) {
            buttonHashMap.put(getSlot(x++, y), new ReportButton(type, target));

            if (x == 8) {
                y++;
                x = 1;
            }
        }
        return buttonHashMap;
    }

    @Override
    public int getSize() {
        return 36;
    }
}
