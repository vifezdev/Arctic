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

import java.util.UUID;

public class ReplyCommand {

    ConversationHandler conversationHandler = Arctic.getInstance().getConversationHandler();

    @Command(name = "", desc = "replies back to a player", usage = "<message>")
    public void execute(@Sender CommandSender sender, @Text String message) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (conversationHandler.getConversations().isEmpty()) {
            sender.sendMessage(CC.translate("&cYou don't have anyone to reply to."));
            return;
        }
        UUID target = conversationHandler.getConversations().get(((Player) sender).getUniqueId());
        if (target == null) {
            sender.sendMessage(CC.translate("&cYou don't have anyone to reply to."));
            return;
        }
        conversationHandler.sendMessage(((Player) sender).getUniqueId(), target, message);
    }

}
