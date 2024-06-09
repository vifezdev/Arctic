package lol.pots.core.disguise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.Document;
import lol.pots.core.Arctic;
import lol.pots.core.rank.Rank;

@AllArgsConstructor
public class DisguiseProfile {

    @Getter
    private String name;
    @Getter
    private Rank rank;
    @Getter
    private String texture;
    @Getter
    private String signature;

    public DisguiseProfile(Document document) {
        this.name = document.getString("name");

        if (document.containsKey("texture")) {
            this.texture = document.getString("texture");
        }

        if (document.containsKey("signature")) {
            this.signature = document.getString("signature");
        }

        if (document.containsKey("rankUuid")) {
            this.rank = Arctic.getInstance().getRankHandler().getRankByName(document.getString("rankUuid"));
        }

    }

    public Document toDocument() {

        final Document document = new Document();

        document.put("name", this.name);

        if (this.texture != null) {
            document.put("texture", this.texture);
        }

        if (this.signature != null) {
            document.put("signature", this.signature);
        }

        if (this.rank != null) {
            document.put("rank", this.rank.getName());
        }

        return document;
    }
}
