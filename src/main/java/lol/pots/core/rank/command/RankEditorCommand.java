package lol.pots.core.rank.command;

import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.handler.RankHandler;
import lol.pots.core.rank.menu.RankEditorMenu;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class RankEditorCommand {

    private final RankHandler rankHandler = Arctic.getInstance().getRankHandler();

    @Command(name = "editor", desc = "edit ranks through a menu")
    @Require("arctic.ranks")
    public void execute(@Sender Player sender) {
        new RankEditorMenu().openMenu(sender);
    }

}
