package lol.pots.core.punishment;

import com.google.gson.JsonObject;


public class PunishmentDeserializer {

    public static Punishment deserialize(JsonObject jsonObject) {
        int id = jsonObject.get("id").getAsInt();
        String addedPunishment = jsonObject.get("addedPunishment").getAsString();
        String addedBy = jsonObject.get("addedBy").getAsString();
        long addedOn = jsonObject.get("addedOn").getAsLong();
        long addedDuration = jsonObject.get("addedDuration").getAsLong();
        String addedReason = jsonObject.get("addedReason").getAsString();
        boolean addedSilent = jsonObject.get("addedSilent").getAsBoolean();
        boolean pardoned = jsonObject.get("pardoned").getAsBoolean();
        String pardonedBy = "";
        String pardonedReason = "";
        long pardonedOn = System.currentTimeMillis();
        if (pardoned) {
            pardonedBy = jsonObject.get("pardonedBy").getAsString();
            pardonedReason = jsonObject.get("pardonedReason").getAsString();
            pardonedOn = jsonObject.get("pardonedOn").getAsLong();
        }
        boolean pardonedSilent = jsonObject.get("pardonedSilent").getAsBoolean();
        return new Punishment(id, addedPunishment, addedBy, addedOn, addedDuration, addedReason, addedSilent, pardoned, pardonedBy, pardonedReason, pardonedOn, pardonedSilent);
    }

}
