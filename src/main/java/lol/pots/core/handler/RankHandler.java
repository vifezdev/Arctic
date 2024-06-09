package lol.pots.core.handler;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.Document;
import lol.pots.core.Arctic;
import lol.pots.core.rank.Rank;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hieu
 * @date 05/10/2023
 */

@Getter
public class RankHandler {

    private List<Rank> ranks;
    private MongoCollection<Document> collection;

    public RankHandler(){
        ranks = new ArrayList<>();
        collection = Arctic.getInstance().getMongoHandler().getDatabase().getCollection("Ranks");
    }

    public static void load(){
        for (Document document : Arctic.getInstance().getRankHandler().getCollection().find()){
            String name = document.getString("name");
            String displayName = document.getString("displayName");
            String prefix = document.getString("prefix");
            String suffix = document.getString("suffix");
            String color = document.getString("color");
            int weight = document.getInteger("weight");
            int durability = document.getInteger("durability");
            boolean disguisable = document.getBoolean("disguisable");
            boolean staff = document.getBoolean("staff");
            Type type = new TypeToken<List<String>>(){}.getType();
            List<String> permissions = new Gson().fromJson(document.getString("permissions"), type);
            List<String> inheritances = new Gson().fromJson(document.getString("inheritances"), type);
            new Rank(name, displayName, prefix, suffix, color, weight, durability, permissions, inheritances , disguisable, staff).save();
        }
        if (Arctic.getInstance().getRankHandler().getDefaultRank() == null){
            String name = Arctic.getInstance().getSettingsConfig().getString("DEFAULT-RANK.NAME");
            String prefix = Arctic.getInstance().getSettingsConfig().getString("DEFAULT-RANK.PREFIX");
            String suffix = Arctic.getInstance().getSettingsConfig().getString("DEFAULT-RANK.SUFFIX");
            String color = Arctic.getInstance().getSettingsConfig().getString("DEFAULT-RANK.COLOR");
            int weight = Arctic.getInstance().getSettingsConfig().getInt("DEFAULT-RANK.WEIGHT");
            int durability = Arctic.getInstance().getSettingsConfig().getInt("DEFAULT-RANK.DURABILITY");
            new Rank(name, name, prefix, suffix, color, weight, durability, new ArrayList<>(), new ArrayList<>() , false, false).save();
        }
    }

    public Rank getRankByName(String name){
        for (Rank rank : ranks){
            if (rank.getName().equalsIgnoreCase(name)) return rank;
        }
        return null;
    }

    public Rank getDefaultRank(){
        String name = Arctic.getInstance().getSettingsConfig().getString("DEFAULT-RANK.NAME");
        return getRankByName(name);
    }

    public void deleteRank(Rank rank){
        ranks.remove(rank);
        collection.findOneAndDelete(Filters.eq("name", rank.getName()));
    }

}