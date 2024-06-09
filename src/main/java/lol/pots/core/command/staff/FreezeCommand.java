package lol.pots.core.command.staff;

import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

import static lol.pots.core.values.Locale.FROZEN_STAFF_FREEZE_MESSAGE;
import static lol.pots.core.values.Locale.FROZEN_STAFF_UNFREEZE_MESSAGE;

public class FreezeCommand {
    @Command(name = "", desc = "Toggles the frozen state of a player")
    @Require("arctic.freeze")
    public void execute(@Sender Player sender, Player target) {
        Profile targetProfile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());

        if (targetProfile.isFrozen()) {
            targetProfile.setFrozen(false);
            sender.sendMessage(CC.translate(FROZEN_STAFF_UNFREEZE_MESSAGE.getString().replace("{player}", target.getName())));
        } else {
            targetProfile.setFrozen(true);
            sender.sendMessage(CC.translate(FROZEN_STAFF_FREEZE_MESSAGE.getString().replace("{player}", target.getName())));
        }
    }
}
