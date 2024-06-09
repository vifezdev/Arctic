package lol.pots.core.rank;

import lol.pots.core.Arctic;
import lol.pots.core.util.command.argument.CommandArg;
import lol.pots.core.util.command.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class RankProvider extends DrinkProvider<Rank> {

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Nullable
    @Override
    public Rank provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) {
        return Arctic.getInstance().getRankHandler().getRankByName(arg.get());
    }

    @Override
    public String argumentDescription() {
        return "rank";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        List<String> suggestions = new ArrayList<>();
        for (Rank rank : Arctic.getInstance().getRankHandler().getRanks()) {
            if (rank.getName().toLowerCase().startsWith(prefix.toLowerCase())) suggestions.add(rank.getName());
        }
        return suggestions;
    }
}
