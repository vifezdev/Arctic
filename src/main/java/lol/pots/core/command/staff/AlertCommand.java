package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.packet.AlertMessagePacket;
import lol.pots.core.util.command.annotation.*;

public class AlertCommand {

    @Command(name = "", desc = "broadcasts a message", usage = "<message> [-r]")
    @Require("arctic.alert")
    public void execute(@Sender CommandSender sender, @Text String message, @Flag('r') boolean raw) {
        message = raw ? message : "&7[&4Alert&7] &r" + message;
        AlertMessagePacket packet = new AlertMessagePacket(message);
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
    }

}
