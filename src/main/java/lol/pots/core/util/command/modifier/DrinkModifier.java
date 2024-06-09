package lol.pots.core.util.command.modifier;

import lol.pots.core.util.command.command.CommandExecution;
import lol.pots.core.util.command.exception.CommandExitMessage;
import lol.pots.core.util.command.parametric.CommandParameter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface DrinkModifier<T> {

    Optional<T> modify(@Nonnull CommandExecution execution, @Nonnull CommandParameter commandParameter, @Nullable T argument) throws CommandExitMessage;

}
