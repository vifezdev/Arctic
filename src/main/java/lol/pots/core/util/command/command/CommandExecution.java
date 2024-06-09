package lol.pots.core.util.command.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import lol.pots.core.util.command.argument.CommandArgs;

import java.util.List;

@Getter
public class CommandExecution {

    private final DrinkCommandService commandService;
    private final CommandSender sender;
    private final List<String> args;
    private final CommandArgs commandArgs;
    private final DrinkCommand command;
    private boolean canExecute = true;

    public CommandExecution(DrinkCommandService commandService, CommandSender sender, List<String> args, CommandArgs commandArgs, DrinkCommand command) {
        this.commandService = commandService;
        this.sender = sender;
        this.args = args;
        this.commandArgs = commandArgs;
        this.command = command;
    }

    public void preventExecution() {
        canExecute = false;
    }

}
