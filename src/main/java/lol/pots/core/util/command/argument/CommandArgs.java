package lol.pots.core.util.command.argument;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.util.command.command.CommandFlag;
import lol.pots.core.util.command.command.DrinkCommandService;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class CommandArgs {

    private final DrinkCommandService commandService;
    private final CommandSender sender;
    private final List<String> args;
    private final String label;
    private final Map<Character, CommandFlag> flags;
    private final ReentrantLock lock = new ReentrantLock();
    private int index = 0;

    public CommandArgs(@Nonnull DrinkCommandService commandService, @Nonnull CommandSender sender, @Nonnull String label, @Nonnull List<String> args,
                       @Nonnull Map<Character, CommandFlag> flags) {
        Preconditions.checkNotNull(commandService, "CommandService cannot be null");
        Preconditions.checkNotNull(sender, "CommandSender cannot be null");
        Preconditions.checkNotNull(label, "Label cannot be null");
        Preconditions.checkNotNull(args, "Command args cannot be null");
        this.commandService = commandService;
        this.sender = sender;
        this.label = label;
        this.args = new ArrayList<>(args);
        this.flags = flags;
    }

    public boolean hasNext() {
        lock.lock();
        try {
            return args.size() > index;
        } finally {
            lock.unlock();
        }
    }

    public String next() {
        lock.lock();
        try {
            return args.get(index++);
        } finally {
            lock.unlock();
        }
    }

    public boolean isSenderPlayer() {
        return sender instanceof Player;
    }

    public Player getSenderAsPlayer() {
        return (Player) sender;
    }


}
