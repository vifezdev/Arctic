package lol.pots.core.command.staff;

import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.packet.PlayerVanishPacket;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;


public class VanishCommand {

    @Command(name = "", desc = "Toggles the frozen state of a player")
    @Require("arctic.vanish")
    public void execute(@Sender Player sender, @OptArg Player target){

        Profile profile;

        boolean self;

        if(target == null){
            self = true;
            profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(sender.getUniqueId());
        }
        else{
            self = false;
            profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());
        }

        Player toVanish = profile.getPlayer();

        PlayerVanishPacket playerVanishPacket = new PlayerVanishPacket(toVanish, profile, self);
        Arctic.getInstance().getRedisHandler().sendPacket(playerVanishPacket);


        
    }
}
