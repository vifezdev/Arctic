package lol.pots.core.punishment.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.punishment.menu.button.PunishmentTypeButton;
import lol.pots.core.util.CC;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.Menu;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class HistoryMenu extends Menu {

    private Profile target;

    @Override
    public boolean isPlaceholder() {
        return true;
    }

    @Override
    public String getTitle(Player player) {
        return CC.translate(target.getUsername() + "'s history (" + target.getPunishments().size() + ")");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttonHashMap = new HashMap<>();
        buttonHashMap.put(15, new PunishmentTypeButton(target, true, PunishmentType.BLACKLIST));
        buttonHashMap.put(14, new PunishmentTypeButton(target, true, PunishmentType.BAN));
        buttonHashMap.put(13, new PunishmentTypeButton(target, true, PunishmentType.KICK));
        buttonHashMap.put(12, new PunishmentTypeButton(target, true, PunishmentType.MUTE));
        buttonHashMap.put(11, new PunishmentTypeButton(target, true, PunishmentType.WARN));
        return buttonHashMap;
    }

    @Override
    public int getSize() {
        return 27;
    }

}
