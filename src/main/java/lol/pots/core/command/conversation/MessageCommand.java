package lol.pots.core.command.conversation;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ConversationHandler;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Sender;
import lol.pots.core.util.command.annotation.Text;

public class MessageCommand {

    ConversationHandler conversationHandler = Arctic.getInstance().getConversationHandler();

    @Command(name = "", desc = "sends a player a message", usage = "<player> <message>")
    public void execute(@Sender CommandSender sender, Player target, @Text String message) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (sender == target) {
            sender.sendMessage(CC.translate("&cYou can't message yourself."));
            return;
        }
        conversationHandler.sendMessage(((Player) sender).getUniqueId(), target.getUniqueId(), message);
    }

}
