package lol.pots.core.command;

import lombok.RequiredArgsConstructor;
import lol.pots.core.Arctic;
import lol.pots.core.command.conversation.MessageCommand;
import lol.pots.core.command.conversation.ReplyCommand;
import lol.pots.core.command.friend.FriendAcceptCommand;
import lol.pots.core.command.friend.FriendAddCommand;
import lol.pots.core.command.friend.FriendCommand;
import lol.pots.core.command.friend.FriendsCommand;
import lol.pots.core.command.ignore.IgnoreAddCommand;
import lol.pots.core.command.ignore.IgnoreCommand;
import lol.pots.core.command.ignore.IgnoreListCommand;
import lol.pots.core.command.ignore.IgnoreRemoveCommand;
import lol.pots.core.command.staff.*;
import lol.pots.core.disguise.command.DisguiseCommand;
import lol.pots.core.disguise.command.UndisguiseCommand;
import lol.pots.core.grant.command.GrantCommand;
import lol.pots.core.grant.command.GrantsClearCommand;
import lol.pots.core.grant.command.GrantsCommand;
import lol.pots.core.grant.command.oGrantCommand;
import lol.pots.core.ladders.Ladder;
import lol.pots.core.ladders.LadderProvider;
import lol.pots.core.note.command.NoteAddCommand;
import lol.pots.core.note.command.NotesCommand;
import lol.pots.core.option.command.ToggleGlobalChatCommand;
import lol.pots.core.option.command.TogglePrivateMessagesCommand;
import lol.pots.core.option.command.ToggleSoundsCommand;
import lol.pots.core.profile.Profile;
import lol.pots.core.profile.ProfileProvider;
import lol.pots.core.profile.permissions.PermissionsAddCommand;
import lol.pots.core.profile.permissions.PermissionsDeleteCommand;
import lol.pots.core.profile.permissions.PermissionsListCommand;
import lol.pots.core.punishment.command.*;
import lol.pots.core.punishment.command.history.HistoryClearCommand;
import lol.pots.core.punishment.command.history.HistoryCommand;
import lol.pots.core.punishment.command.pardon.UnbanCommand;
import lol.pots.core.punishment.command.pardon.UnblacklistCommand;
import lol.pots.core.punishment.command.pardon.UnipbanCommand;
import lol.pots.core.punishment.command.pardon.UnmuteCommand;
import lol.pots.core.rank.Rank;
import lol.pots.core.rank.RankProvider;
import lol.pots.core.rank.command.*;
import lol.pots.core.tags.Tag;
import lol.pots.core.tags.TagProvider;
import lol.pots.core.tags.command.*;
import lol.pots.core.util.command.CommandService;

@RequiredArgsConstructor
public class CommandManager {
    public final Arctic plugin;
    public final CommandService drink;

    public void init() {
        this.registerProviders();

        drink.register(new GrantsCommand(), "grants")
                .registerSub(new GrantsClearCommand());
        drink.register(new GrantCommand(), "grant");
        drink.register(new oGrantCommand(), "ogrant");
        drink.register(new RanksCommand(), "ranks");
        drink.register(new RankCommand(), "rank")
                .registerSub(new RankCreateCommand())
                .registerSub(new RankDeleteCommand())
                .registerSub(new RankAddPermissionCommand())
                .registerSub(new RankDeletePermissionCommand())
                .registerSub(new RankInformationCommand())
                .registerSub(new RankInheritanceCommand())
                .registerSub(new RankSetColorCommand())
                .registerSub(new RankSetDurabilityCommand())
                .registerSub(new RankSetDisplayNameCommand())
                .registerSub(new RankSetPrefixCommand())
                .registerSub(new RankSetSuffixCommand())
                .registerSub(new RankSetWeightCommand())
                .registerSub(new RankStaffCommand())
                .registerSub(new RankEditorCommand())
                .registerSub(new RankUninheritanceCommand());
        drink.register(new TagCommand(), "tag")
                .registerSub(new TagInformationCommand())
                .registerSub(new TagListCommand())
                .registerSub(new TagCreateCommand())
                .registerSub(new TagDeleteCommand())
                .registerSub(new TagSetPrefixCommand());
        drink.register(new TagsCommand(), "tags");
        drink.register(new NotesCommand(), "notes");
        drink.register(new NoteAddCommand(), "note");
        drink.register(new DisguiseCommand(), "disguise", "nick", "dis");
        drink.register(new VanishCommand(), "vanish", "v");
        drink.register(new UndisguiseCommand(), "undisguise", "unnick", "undis");
        drink.register(new PermissionsAddCommand(), "permissions")
                .registerSub(new PermissionsDeleteCommand())
                .registerSub(new PermissionsListCommand());
        drink.register(new HistoryCommand(), "history", "punishments")
                .registerSub(new HistoryClearCommand());
        drink.register(new BlacklistCommand(), "blacklist");
        drink.register(new UnblacklistCommand(), "unblacklist");
        drink.register(new BanCommand(), "ban");
        drink.register(new PunishCommand(), "punish");
        drink.register(new IPBanCommand(), "ipban");
        drink.register(new UnipbanCommand(), "unipban");
        drink.register(new UnbanCommand(), "unban");
        drink.register(new KickCommand(), "kick");
        drink.register(new KickPlayers(), "kickall", "kickplayers");
        drink.register(new MuteCommand(), "mute");
        drink.register(new UnmuteCommand(), "unmute");
        drink.register(new WarnCommand(), "warn");
        drink.register(new StaffChatCommand(), "sc", "staffchat");
        drink.register(new SudoCommand(), "sudo");
        drink.register(new SudoAllCommand(), "sudoall");
        drink.register(new AltsCommand(), "alts");
        drink.register(new ListCommand(), "list");
        drink.register(new RequestCommand(), "request", "helpop");
        drink.register(new ReportCommand(), "report");
        drink.register(new SeenCommand(), "seen");
        drink.register(new AlertCommand(), "alert", "broadcast", "bc");
        drink.register(new MessageCommand(), "message", "msg");
        drink.register(new ReplyCommand(), "reply", "r");
        drink.register(new TogglePrivateMessagesCommand(), "toggleprivatemessages", "toggleprivatemessage", "togglepms", "togglepm", "tpm");
        drink.register(new ToggleSoundsCommand(), "togglesounds", "togglesound", "sounds", "sound");
        drink.register(new ToggleGlobalChatCommand(), "toggleglobalchat", "togglechat", "tgc");
        drink.register(new MoreCommand(), "more");
        drink.register(new DayCommand(), "day");
        drink.register(new NightCommand(), "night");
        drink.register(new PingCommand(), "ping");
        drink.register(new ClearCommand(), "clear");
        drink.register(new TeleportCommand(), "teleport", "tp");
        drink.register(new TeleportHereCommand(), "teleporthere", "tphere");
        drink.register(new TeleportLocationCommand(), "tploc", "tpl", "tppos", "tplocation");
        drink.register(new FreezeCommand(), "freeze", "unfreeze");
        drink.register(new ReloadCommand(), "arcticreload");
        drink.register(new HealCommand(), "heal");
        drink.register(new FeedCommand(), "feed");
        drink.register(new CrashCommand(), "crash");
        drink.register(new FlyCommand(), "fly");
        drink.register(new IgnoreCommand(), "ignore")
                .registerSub(new IgnoreAddCommand())
                .registerSub(new IgnoreRemoveCommand())
                .registerSub(new IgnoreListCommand());
        drink.register(new FriendCommand(), "friend")
                .registerSub(new FriendAddCommand())
                .registerSub(new FriendAcceptCommand());
        drink.register(new FriendsCommand(), "friends");
        drink.register(new GamemodeSurvivalCommand(), "gms", "gm0");
        drink.register(new GamemodeCreativeCommand(), "gmc", "gm1");
        drink.register(new GamemodeAdventureCommand(), "gma", "gm2");
        drink.registerCommands();
    }

    public void registerProviders() {
        drink.bind(Profile.class).toProvider(new ProfileProvider());
        drink.bind(Ladder.class).toProvider(new LadderProvider());
        drink.bind(Rank.class).toProvider(new RankProvider());
        drink.bind(Tag.class).toProvider(new TagProvider());
    }
}