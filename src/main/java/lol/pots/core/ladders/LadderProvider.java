package lol.pots.core.ladders;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import lol.pots.core.Arctic;
import lol.pots.core.util.command.argument.CommandArg;
import lol.pots.core.util.command.exception.CommandExitMessage;
import lol.pots.core.util.command.parametric.DrinkProvider;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class LadderProvider extends DrinkProvider<Ladder> {

    @Override
    public boolean doesConsumeArgument() {
        return false;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Nullable
    @Override
    public Ladder provide(@NotNull CommandArg arg, @NotNull List<? extends Annotation> annotations) throws CommandExitMessage {
        return Arctic.getInstance().getLadderHandler().getLadderByName(arg.get());
    }

    @Override
    public String argumentDescription() {
        return null;
    }

    @Override
    public List<String> getSuggestions(@NotNull String prefix) {
        return Arctic.getInstance().getLadderHandler().getLadders().stream()
                .map(Ladder::getName)
                .collect(Collectors.toList());
    }
}
