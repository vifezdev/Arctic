package lol.pots.core.profile;

import lol.pots.core.Arctic;
import lol.pots.core.util.command.argument.CommandArg;
import lol.pots.core.util.command.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ProfileProvider extends DrinkProvider<Profile> {

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
    public Profile provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) {
        return Arctic.getInstance().getProfileHandler().getProfileByUsername(arg.get());
    }

    @Override
    public String argumentDescription() {
        return "profile";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        List<String> suggestions = new ArrayList<>();
        for (Profile profile : Arctic.getInstance().getProfileHandler().getProfiles()) {
            if (profile.getUsername().toLowerCase().startsWith(prefix.toLowerCase()))
                suggestions.add(profile.getUsername());
        }
        return suggestions;
    }
}
