package lol.pots.core.handler;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ProfileHandler {

    private List<Profile> profiles;
    private MongoCollection<Document> collection;

    public ProfileHandler(){
        profiles = new ArrayList<>();
        collection = Arctic.getInstance().getMongoHandler().getDatabase().getCollection("Profiles");
    }

    public Profile getProfileByUsername(String username){
        Player player = Bukkit.getPlayer(username);
        if (player != null) {
            for (Profile profile : profiles){
                if (profile.getUsername().equalsIgnoreCase(username)) return profile;
            }
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer.hasPlayedBefore()) return new Profile(username);
        return null;
    }

    public Profile getProfileByUUID(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            for (Profile profile : profiles){
                if (profile.getUuid().equals(uuid)) return profile;
            }
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.hasPlayedBefore()) return new Profile(uuid);
        return null;
    }

    public List<Profile> getProfilesByLastSeenAddress(String address){
        List<Profile> returnProfiles = new ArrayList<>();
        Bson filter = Filters.eq("lastSeenAddress", address);
        collection.find(filter).forEach((Block<? super Document>) doc -> {
            returnProfiles.add(new Profile(UUID.fromString(doc.getString("uuid"))));
        });
        return returnProfiles;
    }

}