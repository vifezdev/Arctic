package lol.pots.core.util.menu.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import lol.pots.core.util.menu.Button;
import lol.pots.core.util.menu.Menu;
import lol.pots.core.util.menu.button.BooleanButton;
import lol.pots.core.util.menu.callback.Callback;

import java.beans.ConstructorProperties;
import java.util.HashMap;
import java.util.Map;

public class ConfirmMenu extends Menu {

    private final String title;
    private final Callback<Boolean> response;

    @ConstructorProperties(value = {"title", "response"})
    public ConfirmMenu(String title, Callback<Boolean> response) {
        this.title = title;
        this.response = response;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();
        buttons.put(11, new BooleanButton(true, this.response));
        buttons.put(15, new BooleanButton(false, this.response));
        for (int i = 0; i < 27; ++i) {
            buttons.putIfAbsent(i, Button.placeholder(Material.STAINED_GLASS_PANE, (byte) 7));
        }
        return buttons;
    }

    @Override
    public String getTitle(Player player) {
        return this.title;
    }
}

