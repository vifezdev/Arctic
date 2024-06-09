package lol.pots.core.util.command.provider.spigot;

import org.bukkit.entity.Player;
import lol.pots.core.util.command.argument.CommandArg;
import lol.pots.core.util.command.exception.CommandExitMessage;
import lol.pots.core.util.command.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class PlayerSenderProvider extends DrinkProvider<Player> {

    public static final PlayerSenderProvider INSTANCE = new PlayerSenderProvider();

    @Override
    public boolean doesConsumeArgument() {
        return false;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    @Nullable
    public Player provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        if (arg.isSenderPlayer()) {
            return arg.getSenderAsPlayer();
        }
        throw new CommandExitMessage("This is a player-only command.");
    }

    @Override
    public String argumentDescription() {
        return "player sender";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        return Collections.emptyList();
    }
}
