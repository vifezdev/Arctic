package lol.pots.core.command.friend;

import org.bukkit.command.CommandSender;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;

public class FriendCommand {

    @Command(name = "", desc = "shows friend commands")
    @Require("arctic.rank")
    public void execute(@Sender CommandSender sender) {
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bFriend Commands"));
        sender.sendMessage(CC.translate(" &3/friend add &7<player>"));
        sender.sendMessage(CC.translate(" &3/friend accept &7<player>"));
        sender.sendMessage(CC.translate(" &3/friend remove &7<player>"));
        sender.sendMessage(CC.CHAT_BAR);
    }

}
