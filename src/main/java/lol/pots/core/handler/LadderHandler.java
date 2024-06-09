package lol.pots.core.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import lol.pots.core.Arctic;
import lol.pots.core.ladders.Ladder;
import lol.pots.core.profile.Profile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LadderHandler {

    @Getter
    List<Ladder> ladders;
    private MongoCollection<Document> collection;
    private Gson gson;

    public LadderHandler() {
        ladders = new ArrayList<>();
        collection = Arctic.getInstance().getMongoHandler().getDatabase().getCollection("Ladders");
        gson = new Gson();
        init();
    }


    public void init() {
        Document document = collection.find().first();
        if (document != null && document.containsKey("data")) {
            String json = document.getString("data");
            Type listType = new TypeToken<List<Ladder>>(){}.getType();
            ladders = gson.fromJson(json, listType);
        }
    }

    public void saveLadders() {
        String json = gson.toJson(ladders);
        collection.updateOne(new Document(), new Document("$set", new Document("data", json)));
    }

    public Ladder getLadderByName(String name) {
        for (Ladder ladder : ladders) {
            if (ladder.getName().equalsIgnoreCase(name)) {
                return ladder;
            }
        }
        return null;
    }

    public void saveLadder(Ladder ladder) {
        ladders.add(ladder);
        saveLadders();
    }

    public void punish(Profile target, Ladder ladder){
        int vl = target.getTotalViolations();
        int punishIndex = Math.min(vl, ladder.getPunishments().size() - 1);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), ladder.getPunishments().get(punishIndex).replace("{player}", target.getUsername()));


    }


}