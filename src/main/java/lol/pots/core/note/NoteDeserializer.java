package lol.pots.core.note;

import com.google.gson.JsonObject;

public class NoteDeserializer {

    public static Note deserialize(JsonObject jsonObject) {
        int id = jsonObject.get("id").getAsInt();
        String addedBy = jsonObject.get("addedBy").getAsString();
        long addedOn = jsonObject.get("addedOn").getAsLong();
        String addedReason = jsonObject.get("addedReason").getAsString();
        return new Note(id, addedBy, addedOn, addedReason);
    }

}
