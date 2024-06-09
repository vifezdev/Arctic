package lol.pots.core.command.staff;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class TeleportLocationCommand {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private double parseCoordinate(String coordStr, double currentCoordinate) {
        if (coordStr.startsWith("~")) {
            try {
                double offset = Double.parseDouble(coordStr.substring(1));
                return roundToHundredths(currentCoordinate + offset);
            } catch (NumberFormatException e) {
                return roundToHundredths(currentCoordinate);  // Default to current coordinate if parsing fails.
            }
        } else {
            return roundToHundredths(Double.parseDouble(coordStr));
        }
    }

    private double roundToHundredths(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Command(name = "", desc = "teleports a player to a location", usage = "[player] <x> <y> <z>")
    @Require("arctic.teleport")
    public void execute(@Sender CommandSender sender, @OptArg Player target, String xStr, String yStr, String zStr) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessage.IN_GAME_COMMAND_ONLY);
            return;
        }

        Player player = (Player) sender;
        Profile profile = profileHandler.getProfileByUUID(player.getUniqueId());

        Player teleportingPlayer = target != null ? target : player;

        double x = parseCoordinate(xStr, teleportingPlayer.getLocation().getX());
        double y = parseCoordinate(yStr, teleportingPlayer.getLocation().getY());
        double z = parseCoordinate(zStr, teleportingPlayer.getLocation().getZ());

        Location location = new Location(teleportingPlayer.getWorld(), x, y, z);
        teleportingPlayer.teleport(location);

        String message;
        if (target != null) {
            Profile targetProfile = profileHandler.getProfileByUUID(target.getUniqueId());
            message = "&aTeleported &r{target} &ato {location}.";
            sender.sendMessage(CC.translate(message
                    .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + target.getName())
                    .replace("{location}", x + ", " + y + ", " + z)));
        } else {
            message = "&aTeleported to {location}.";
            sender.sendMessage(CC.translate(message.replace("{location}", x + ", " + y + ", " + z)));
        }
    }
}
