package lol.pots.core.punishment;

import com.google.gson.JsonObject;

public class PunishmentSerializer {

    public static JsonObject serialize(Punishment punishment) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", punishment.getId());
        jsonObject.addProperty("addedPunishment", punishment.getAddedPunishment());
        jsonObject.addProperty("addedBy", punishment.getAddedBy());
        jsonObject.addProperty("addedOn", punishment.getAddedOn());
        jsonObject.addProperty("addedDuration", punishment.getAddedDuration());
        jsonObject.addProperty("addedReason", punishment.getAddedReason());
        jsonObject.addProperty("addedSilent", punishment.isAddedSilent());
        jsonObject.addProperty("pardoned", punishment.isPardoned());
        if (punishment.isPardoned()) {
            jsonObject.addProperty("pardonedBy", punishment.getPardonedBy());
            jsonObject.addProperty("pardonedReason", punishment.getPardonedReason());
            jsonObject.addProperty("pardonedOn", punishment.getPardonedOn());
        }
        jsonObject.addProperty("pardonedSilent", punishment.isPardonedSilent());
        return jsonObject;
    }

}
