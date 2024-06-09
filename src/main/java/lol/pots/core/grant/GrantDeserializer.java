package lol.pots.core.grant;

import com.google.gson.JsonObject;

public class GrantDeserializer {

    public static Grant deserialize(JsonObject jsonObject) {
        int id = jsonObject.get("id").getAsInt();
        String addedRank = jsonObject.get("addedRank").getAsString();
        String addedBy = jsonObject.get("addedBy").getAsString();
        long addedOn = jsonObject.get("addedOn").getAsLong();
        long addedDuration = jsonObject.get("addedDuration").getAsLong();
        String addedReason = jsonObject.get("addedReason").getAsString();
        boolean pardoned = jsonObject.get("pardoned").getAsBoolean();
        String pardonedBy = "";
        String pardonedReason = "";
        long pardonedOn = System.currentTimeMillis();
        if (pardoned) {
            pardonedBy = jsonObject.get("pardonedBy").getAsString();
            pardonedReason = jsonObject.get("pardonedReason").getAsString();
            pardonedOn = jsonObject.get("pardonedOn").getAsLong();
        }
        return new Grant(id, addedRank, addedBy, addedOn, addedDuration, addedReason, pardoned, pardonedBy, pardonedReason, pardonedOn);
    }

}
