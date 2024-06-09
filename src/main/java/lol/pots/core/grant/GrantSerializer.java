package lol.pots.core.grant;

import com.google.gson.JsonObject;

public class GrantSerializer {

    public static JsonObject serialize(Grant grant) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", grant.getId());
        jsonObject.addProperty("addedRank", grant.getAddedRank());
        jsonObject.addProperty("addedBy", grant.getAddedBy());
        jsonObject.addProperty("addedOn", grant.getAddedOn());
        jsonObject.addProperty("addedDuration", grant.getAddedDuration());
        jsonObject.addProperty("addedReason", grant.getAddedReason());
        jsonObject.addProperty("pardoned", grant.isPardoned());
        if (grant.isPardoned()) {
            jsonObject.addProperty("pardonedBy", grant.getPardonedBy());
            jsonObject.addProperty("pardonedReason", grant.getPardonedReason());
            jsonObject.addProperty("pardonedOn", grant.getPardonedOn());
        }
        return jsonObject;
    }

}
