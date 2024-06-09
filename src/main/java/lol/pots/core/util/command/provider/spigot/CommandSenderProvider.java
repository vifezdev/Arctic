package lol.pots.core.util.command.provider.spigot;

import org.bukkit.command.CommandSender;
import lol.pots.core.util.command.argument.CommandArg;
import lol.pots.core.util.command.exception.CommandExitMessage;
import lol.pots.core.util.command.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class CommandSenderProvider extends DrinkProvider<CommandSender> {

    public static final CommandSenderProvider INSTANCE = new CommandSenderProvider();

    @Override
    public boolean doesConsumeArgument() {
        return false;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean allowNullArgument() {
        return true;
    }

    @Nullable
    @Override
    public CommandSender defaultNullValue() {
        return null;
    }

    @Override
    @Nullable
    public CommandSender provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        return arg.getSender();
    }

    @Override
    public String argumentDescription() {
        return "sender";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        return Collections.emptyList();
    }
}
