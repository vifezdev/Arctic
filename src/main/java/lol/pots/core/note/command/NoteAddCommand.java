package lol.pots.core.note.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.messages.ErrorMessage;
import lol.pots.core.note.Note;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.util.command.annotation.Command;
import lol.pots.core.util.command.annotation.Require;
import lol.pots.core.util.command.annotation.Sender;


public class NoteAddCommand {


    @Command(name = "", desc = "shows note commands")
    @Require("arctic.notes")
    public void execute(@Sender CommandSender sender) {

        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&bNote Commands"));
        sender.sendMessage(CC.CHAT_BAR);

        sender.sendMessage(CC.translate(" &7▪ &f/note add <player> <note>"));
        sender.sendMessage(CC.translate(" &7▪ &f/note remove <player> <noteId>"));
        sender.sendMessage(CC.translate(" &7▪ &f/note list <player>"));
        sender.sendMessage(CC.translate(" &7▪ &f/note search <query>"));

        sender.sendMessage(CC.CHAT_BAR);

    }

    @Command(name = "add", desc = "adds a note to a player", usage = "<player> <note>")
    @Require("arctic.notes")
    public void execute(@Sender CommandSender sender, Profile target, String reason) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }

        int id = target.getNotes().size() + 1;
        String addedBy;
        if (sender instanceof Player) {
            Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(((Player) sender).getUniqueId());
            addedBy = profile.getActiveGrant().getRank().getColor() + sender.getName();
        } else {
            addedBy = "&4Console";
        }
        long addedOn = System.currentTimeMillis();
        Note note = new Note(id, addedBy, addedOn, reason);
        target.getNotes().add(note);
        sender.sendMessage(CC.translate("&aAdded a note to " + target.getUsername()));
    }

    @Command(name = "remove", desc = "removes a note from a player", usage = "<player> <noteId>")
    @Require("arctic.notes")
    public void removeNote(@Sender CommandSender sender, Profile target, int noteId) {
        if (target == null) {
            sender.sendMessage(ErrorMessage.PROFILE_NOT_FOUND);
            return;
        }

        Note note = target.getNotes().stream()
                .filter(n -> n.getId() == noteId)
                .findFirst()
                .orElse(null);

        if (note == null) {
            sender.sendMessage(CC.translate("&cA note with id &f" + noteId + " &ccould not be found."));
            return;
        }

        target.getNotes().remove(note);
        sender.sendMessage(CC.translate("&aRemoved note &f" + noteId + " &afrom " + target.getUsername()));
    }



}
