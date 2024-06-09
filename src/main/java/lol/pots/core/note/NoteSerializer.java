package lol.pots.core.note;

import com.google.gson.JsonObject;

public class NoteSerializer {

    public static JsonObject serialize(Note note) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", note.getId());
        jsonObject.addProperty("addedBy", note.getAddedBy());
        jsonObject.addProperty("addedOn", note.getAddedOn());
        jsonObject.addProperty("addedReason", note.getAddedReason());
        return jsonObject;
    }

}
