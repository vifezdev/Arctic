package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.ladders.Ladder;
import lol.pots.core.profile.Profile;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.*;

public class PunishCommand {
    @Command(name = "", desc = "punishes a player", usage = "<player> <ladder>")
    @Require("arctic.punish")
    public void execute(@Sender CommandSender sender, Profile target, Ladder ladder) {

        if (target.getActivePunishment(PunishmentType.IP_BAN) != null ||  target.getActivePunishment(PunishmentType.BAN) != null || target.getActivePunishment(PunishmentType.MUTE) != null || target.getActivePunishment(PunishmentType.BLACKLIST) != null ) {
            sender.sendMessage(CC.translate("&cThat player is already actively punished and at &b" + target.getTotalViolations() + " &cviolations!"));
            sender.sendMessage(CC.translate("&cUse the &b-f &c flag to force this punishment.") );
            return;
        }
        Arctic.getInstance().getLadderHandler().punish(target, ladder);
        sender.sendMessage(CC.translate("&aPunished &b" + target.getFancyUsername() + "&a!" + " &7(&b" + target.getTotalViolations() + "&7)"));

    }
}