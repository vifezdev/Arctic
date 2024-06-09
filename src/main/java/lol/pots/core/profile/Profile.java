package lol.pots.core.profile;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import lol.pots.core.Arctic;
import lol.pots.core.chat.ChatChannel;
import lol.pots.core.disguise.DisguiseProfile;
import lol.pots.core.disguise.procedure.DisguiseProcedure;
import lol.pots.core.grant.Grant;
import lol.pots.core.grant.GrantDeserializer;
import lol.pots.core.grant.GrantSerializer;
import lol.pots.core.note.Note;
import lol.pots.core.note.NoteDeserializer;
import lol.pots.core.note.NoteSerializer;
import lol.pots.core.procedure.Procedure;
import lol.pots.core.profile.settings.ProfileSettings;
import lol.pots.core.profile.settings.StaffSettings;
import lol.pots.core.punishment.Punishment;
import lol.pots.core.punishment.PunishmentDeserializer;
import lol.pots.core.punishment.PunishmentSerializer;
import lol.pots.core.punishment.PunishmentType;
import lol.pots.core.rank.Rank;
import lol.pots.core.tags.Tag;
import lol.pots.core.util.CC;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Profile {

    private UUID uuid;
    private String username, displayName, lastSeenServer, lastSeenAddress;
    private List<Grant> grants;
    private List<Punishment> punishments;
    private List<Note> notes;
    private Tag tag;
    private List<String> ipAddresses, individualPermissions, totalPermissions;
    private List<UUID> ignored, friends, friendRequests;
    private long lastSeenOn, lastRequestOn, lastReportOn;
    private boolean frozen;
    private boolean vanished;
    private ProfileSettings settings;
    private StaffSettings staffSettings;
    private DisguiseProfile disguiseProfile;
    private DisguiseProcedure disguiseProcedure;
    int totalViolations;

    private Procedure procedure;
    private ChatChannel chatChannel;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        username = Bukkit.getOfflinePlayer(uuid).getName();
        grants = new ArrayList<>();
        punishments = new ArrayList<>();
        notes = new ArrayList<>();
        tag = null;
        disguiseProfile = null;
        frozen = false;
        vanished = false;
        individualPermissions = new ArrayList<>();
        totalPermissions = new ArrayList<>();
        ignored = new ArrayList<>();
        friends = new ArrayList<>();
        friendRequests = new ArrayList<>();
        lastSeenOn = System.currentTimeMillis();
        lastSeenServer = "None";
        lastRequestOn = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(Arctic.getInstance().getSettingsConfig().getInt("STAFF.REQUEST.COOL-DOWN"));
        lastReportOn = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(Arctic.getInstance().getSettingsConfig().getInt("STAFF.REPORT.COOL-DOWN"));
        settings = new ProfileSettings(true, true, true);
        staffSettings = new StaffSettings(false, false, false);
        procedure = null;
        chatChannel = ChatChannel.DEFAULT;
        displayName = "";
        lastSeenAddress = "";
        ipAddresses = new ArrayList<>();
        load();
    }

    public Profile(String username) {
        uuid = Bukkit.getOfflinePlayer(username).getUniqueId();
        this.username = username;
        grants = new ArrayList<>();
        punishments = new ArrayList<>();
        notes = new ArrayList<>();
        tag = null;
        disguiseProfile = null;
        individualPermissions = new ArrayList<>();
        totalPermissions = new ArrayList<>();
        ignored = new ArrayList<>();
        friends = new ArrayList<>();
        friendRequests = new ArrayList<>();
        lastSeenOn = System.currentTimeMillis();
        lastSeenServer = "None";
        lastRequestOn = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(Arctic.getInstance().getSettingsConfig().getInt("STAFF.REQUEST.COOL-DOWN"));
        lastReportOn = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(Arctic.getInstance().getSettingsConfig().getInt("STAFF.REPORT.COOL-DOWN"));
        settings = new ProfileSettings(true, true, true);
        staffSettings = new StaffSettings(false, false, false);
        procedure = null;
        chatChannel = ChatChannel.DEFAULT;
        displayName = "";
        lastSeenAddress = "";
        ipAddresses = new ArrayList<>();
        totalViolations = 0;
        load();
    }

    public boolean isOnRequestCooldown() {
        return System.currentTimeMillis() <= lastRequestOn + TimeUnit.SECONDS.toMillis(Arctic.getInstance().getSettingsConfig().getInt("STAFF.REQUEST.COOL-DOWN"));
    }

    public boolean isOnReportCooldown() {
        return System.currentTimeMillis() <= lastReportOn + TimeUnit.SECONDS.toMillis(Arctic.getInstance().getSettingsConfig().getInt("STAFF.REPORT.COOL-DOWN"));
    }

    public Grant getActiveGrant() {
        Grant returnGrant = null;
        grants.sort(Comparator.comparingInt(Grant::getId));
        for (Grant grant : grants) {
            if (!grant.isExpired() && !grant.isPardoned() && grant.getRank() != null) {
                returnGrant = grant;
            }
        }
        return returnGrant;
    }

    public Punishment getActivePunishment(PunishmentType punishmentType) {
        Punishment returnPunishment = null;
        punishments.sort(Comparator.comparingInt(Punishment::getId));
        for (Punishment punishment : punishments) {
            if (!punishment.isExpired() && !punishment.isPardoned() && punishment.getPunishmentType() == punishmentType) {
                returnPunishment = punishment;
            }
        }
        return returnPunishment;
    }

    public List<Punishment> getPunishmentsList(PunishmentType type) {
        List<Punishment> returnPunishmentsList = new ArrayList<>();
        for (Punishment punishment : punishments) {
            if (punishment.getPunishmentType() == type) returnPunishmentsList.add(punishment);
        }
        return returnPunishmentsList;
    }



    public void recalculatePermissions() {
        PermissionAttachment permissionAttachment = Bukkit.getPlayer(uuid).addAttachment(Arctic.getInstance());
        totalPermissions.addAll(getActiveGrant().getRank().getPermissions());
        for (String individualPermission : individualPermissions) {
            if (!totalPermissions.contains(individualPermission)) totalPermissions.add(individualPermission);
        }
        for (String inheritanceName : getActiveGrant().getRank().getInheritances()) {
            Rank inheritance = Arctic.getInstance().getRankHandler().getRankByName(inheritanceName);
            for (String permission : inheritance.getPermissions()) {
                if (!totalPermissions.contains(permission)) totalPermissions.add(permission);
            }
        }
        for (String permission : totalPermissions) {
            permissionAttachment.setPermission(permission, true);
        }
    }

    public void updateDisplayName() {
        Grant activeGrant = getActiveGrant();
        Rank activeRank = activeGrant.getRank();
        displayName = CC.translate(activeRank.getPrefix() + activeRank.getColor() + username + activeRank.getSuffix());
        Bukkit.getPlayer(uuid).setDisplayName(displayName);
    }

    public void reload() {
        updateDisplayName();
        recalculatePermissions();
    }

    public void load() {
        Document document = Arctic.getInstance().getProfileHandler().getCollection().find(Filters.eq("uuid", uuid.toString())).first();
        if (document == null) {
            Rank rank = Arctic.getInstance().getRankHandler().getDefaultRank();
            long addedOn = System.currentTimeMillis();
            Grant grant = new Grant(grants.size() + 1, rank.getName(), "&4Console", addedOn, Integer.MAX_VALUE, "Console Grant", false, "", "", addedOn);
            grants.add(grant);
            return;
        }
        uuid = UUID.fromString(document.getString("uuid"));
        username = document.getString("username");
        lastSeenAddress = document.getString("lastSeenAddress");
        ipAddresses = new Gson().fromJson(document.getString("ipAddresses"), new TypeToken<List<String>>() {
        }.getType());
        for (JsonElement element : new JsonParser().parse(document.getString("grants")).getAsJsonArray()) {
            JsonObject object = element.getAsJsonObject();
            Grant grant = GrantDeserializer.deserialize(object);
            Rank rank = Arctic.getInstance().getRankHandler().getRankByName(grant.getAddedRank());
            if (rank == null) continue;
            grants.add(grant);
        }
        for (JsonElement element : new JsonParser().parse(document.getString("punishments")).getAsJsonArray()) {
            JsonObject object = element.getAsJsonObject();
            punishments.add(PunishmentDeserializer.deserialize(object));
        }
        for (JsonElement element : new JsonParser().parse(document.getString("notes")).getAsJsonArray()) {
            JsonObject object = element.getAsJsonObject();
            notes.add(NoteDeserializer.deserialize(object));
        }
        if (document.getString("prefix") != null)
            tag = Arctic.getInstance().getTagHandler().getTagByName(document.getString("tag"));
        individualPermissions = new Gson().fromJson(document.getString("individualPermissions"), new TypeToken<List<String>>() {
        }.getType());
        List<String> ignoreStringList = new Gson().fromJson(document.getString("ignored"), new TypeToken<List<String>>() {
        }.getType());
        for (String uuidString : ignoreStringList) {
            ignored.add(UUID.fromString(uuidString));
        }
        List<String> friendsStringList = new Gson().fromJson(document.getString("friends"), new TypeToken<List<String>>() {
        }.getType());
        for (String uuidString : friendsStringList) {
            friends.add(UUID.fromString(uuidString));
        }
        List<String> friendRequestsStringList = new Gson().fromJson(document.getString("friendRequests"), new TypeToken<List<String>>() {
        }.getType());
        for (String uuidString : friendRequestsStringList) {
            friendRequests.add(UUID.fromString(uuidString));
        }
        lastSeenOn = document.getLong("lastSeenOn");
        lastSeenServer = document.getString("lastSeenServer");
        lastRequestOn = document.getLong("lastRequestOn");
        lastReportOn = document.getLong("lastReportOn");
        totalViolations = document.getInteger("totalViolations");

        Document settingsDocument = (Document) document.get("settings");
        settings.setPrivateMessages(settingsDocument.getBoolean("privateMessages"));
        settings.setPrivateMessagingSounds(settingsDocument.getBoolean("privateMessagingSounds"));
        settings.setGlobalChat(settingsDocument.getBoolean("globalChat"));
        Document staffSettingsDocument = (Document) document.get("staffSettings");
        staffSettings.setStaffMode(staffSettingsDocument.getBoolean("staffMode"));
        staffSettings.setStaffChat(staffSettingsDocument.getBoolean("staffChat"));
        staffSettings.setVanished(staffSettingsDocument.getBoolean("vanished"));
    }

    public void save() {
        Document document = new Document();
        document.append("uuid", uuid.toString());
        document.append("username", username);
        document.append("lastSeenAddress", lastSeenAddress);
        document.append("ipAddresses", ipAddresses.toString());

        JsonArray grantsJsonArray = new JsonArray();
        for (Grant grant : grants) {
            Rank rank = Arctic.getInstance().getRankHandler().getRankByName(grant.getAddedRank());
            if (rank == null) continue;
            grantsJsonArray.add(GrantSerializer.serialize(grant));
        }
        document.append("grants", grantsJsonArray.toString());
        JsonArray punishmentsJsonArray = new JsonArray();
        for (Punishment punishment : punishments) {
            punishmentsJsonArray.add(PunishmentSerializer.serialize(punishment));
        }
        document.append("punishments", punishmentsJsonArray.toString());
        JsonArray notesJsonArray = new JsonArray();
        for (Note note : notes) {
            notesJsonArray.add(NoteSerializer.serialize(note));
        }
        document.append("notes", punishmentsJsonArray.toString());
        if (tag != null) {
            document.append("tag", tag.getName());
        }
        document.append("individualPermissions", individualPermissions.toString());
        document.append("ignored", ignored.toString());
        document.append("friends", friends.toString());
        document.append("friendRequests", friends.toString());
        document.append("lastSeenOn", lastSeenOn);
        document.append("lastSeenServer", lastSeenServer);
        document.append("lastRequestOn", lastRequestOn);
        document.append("lastReportOn", lastReportOn);
        document.append("totalViolations", totalViolations);
        Document settingsDocument = new Document();
        settingsDocument.append("privateMessages", settings.isPrivateMessages());
        settingsDocument.append("privateMessagingSounds", settings.isPrivateMessagingSounds());
        settingsDocument.append("globalChat", settings.isGlobalChat());
        document.append("settings", settingsDocument);
        Document staffSettingsDocument = new Document();
        staffSettingsDocument.append("staffMode", staffSettings.isStaffMode());
        staffSettingsDocument.append("vanished", staffSettings.isVanished());
        staffSettingsDocument.append("staffChat", staffSettings.isStaffChat());
        document.append("staffSettings", staffSettingsDocument);

        Arctic.getInstance().getProfileHandler().getCollection().replaceOne(Filters.eq("uuid", uuid.toString()), document, new ReplaceOptions().upsert(true));
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isDisguised() {
        return this.disguiseProfile != null;
    }

    public String getFancyUsername() {
        return getActiveGrant().getRank().getColor() + getUsername();
    }


}
