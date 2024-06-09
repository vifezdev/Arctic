package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class ClearCommand {

    @Command(name = "", desc = "clears a player's inventory", usage = "[player]")
    @Require("arctic.clear")
    public void execute(@Sender CommandSender sender, @OptArg Player target) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (target == null) {
            target = (Player) sender;
        }
        target.getInventory().setArmorContents(new ItemStack[4]);
        target.getInventory().clear();
        target.updateInventory();
        Profile targetProfile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());
        String message = "&aCleared &r{target}&a's inventory.";
        target.sendMessage(CC.translate(message
                .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())));
    }

}
