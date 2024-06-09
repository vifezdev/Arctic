package lol.pots.core.tags;

import lol.pots.core.Arctic;
import lol.pots.core.util.command.argument.CommandArg;
import lol.pots.core.util.command.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class TagProvider extends DrinkProvider<Tag> {

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
    public Tag provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) {
        return Arctic.getInstance().getTagHandler().getTagByName(arg.get());
    }

    @Override
    public String argumentDescription() {
        return "prefix";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        List<String> suggestions = new ArrayList<>();
        for (Tag tag1 : Arctic.getInstance().getTagHandler().getTags()) {
            if (tag1.getName().toLowerCase().startsWith(prefix.toLowerCase())) suggestions.add(tag1.getName());
        }
        return suggestions;
    }
}
