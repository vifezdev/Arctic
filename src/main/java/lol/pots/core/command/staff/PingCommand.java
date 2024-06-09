package lol.pots.core.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Sender;

import static lol.pots.core.values.Locale.PING_OTHER;
import static lol.pots.core.values.Locale.PING_SELF;

public class PingCommand {

    @Command(name="", desc = "Gets the players ping.")
    public void execute(@Sender CommandSender commandSender, @OptArg Player player){
        if(player != null){
            int ping = ((CraftPlayer) player).getHandle().ping;
            Profile target = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
            commandSender.sendMessage(CC.translate(PING_OTHER.getString().replace("{player}",target.getFancyUsername()).replace("{ping}",Integer.toString(ping))));

        }
        else{
            if(commandSender instanceof Player){
                Player self = (Player) commandSender;

                int ping = ((CraftPlayer) self).getHandle().ping;
                commandSender.sendMessage(CC.translate(PING_SELF.getString().replace("{ping}",Integer.toString(ping))));
            }
        }

    }

}
