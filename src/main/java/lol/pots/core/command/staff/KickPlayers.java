package lol.pots.core.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import lol.pots.core.Arctic;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

import java.util.concurrent.atomic.AtomicInteger;

import static lol.pots.core.values.Locale.SERVERKICK;

public class KickPlayers {
    @Command(name = "", desc = "kicks non staff players")
    @Require("arctic.kickplayers")
    public void execute(@Sender CommandSender sender){
        AtomicInteger count = new AtomicInteger();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(!Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId()).getActiveGrant().getRank().isStaff()){
                player.kickPlayer(CC.translate(SERVERKICK.getString()));
                count.getAndIncrement();
            }
        });
        sender.sendMessage(CC.translate( count + " &aplayers" + " &ahave sucessfully been kicked."));
    }
}