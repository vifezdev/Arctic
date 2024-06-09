package lol.pots.core.rank;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import lol.pots.core.Arctic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zique
 * @date 17/02/2024
 */

@Getter @Setter
public class Rank {

    private String name, displayName, prefix, suffix, color;
    private int weight, durability;
    private List<String> permissions, inheritances;
    private boolean staff, disguisable;

    public Rank(String name){
        this.name = name;
        displayName = name;
        prefix = "";
        suffix = "";
        color = "&f";
        weight = 0;
        durability = 0;
        permissions = new ArrayList<>();
        inheritances = new ArrayList<>();
        disguisable = false;
        staff = false;
        Arctic.getInstance().getRankHandler().getRanks().add(this);
    }

    public Rank(String name, String displayName, String prefix, String suffix, String color, int weight, int durability, List<String> permissions, List<String> inheritances, boolean disguisable, boolean staff){
        this.name = name;
        this.displayName = displayName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.color = color;
        this.weight = weight;
        this.durability = durability;
        this.permissions = permissions;
        this.inheritances = inheritances;
        this.disguisable = disguisable;
        this.staff = staff;
        Arctic.getInstance().getRankHandler().getRanks().add(this);
    }

    public void save(){
        Document document = new Document();
        document.append("name", name);
        document.append("displayName", displayName);
        document.append("prefix", prefix);
        document.append("suffix", suffix);
        document.append("color", color);
        document.append("weight", weight);
        document.append("durability", durability);
        document.append("permissions", permissions.toString());
        document.append("inheritances", inheritances.toString());
        document.append("disguisable", disguisable);
        document.append("staff", staff);
        Arctic.getInstance().getRankHandler().getCollection().replaceOne(Filters.eq("name", name), document, new ReplaceOptions().upsert(true));
    }


}