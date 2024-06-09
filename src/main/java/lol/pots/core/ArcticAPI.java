package lol.pots.core;

import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.tags.Tag;

import java.util.UUID;

public class ArcticAPI {

    static Arctic instance = Arctic.getInstance();

    public static Profile getProfileByUUID(UUID uuid) {
        return instance.getProfileHandler().getProfileByUUID(uuid);
    }

    public static Profile getProfileByUsername(String username) {
        return instance.getProfileHandler().getProfileByUsername(username);
    }

    public static String getFancyUsername(Profile profile) {
        return profile.getActiveGrant().getRank().getColor() + profile.getUsername();
    }

    public static String getDisguiseName(Profile profile) {
        return profile.getDisguiseProfile().getName();
    }
    public static String getFancyDisguiseUsername(Profile profile) {
        return profile.getDisguiseProfile().getRank().getColor() + profile.getDisguiseProcedure().getName();
    }

    public static Rank getRankByName(String name) {
        return instance.getRankHandler().getRankByName(name);
    }

    public static boolean isDefaultRank(Rank rank) {
        return rank.getName().equalsIgnoreCase(Arctic.getInstance().getRankHandler().getDefaultRank().getName());
    }

    public static Tag getTagByName(String name) {
        return instance.getTagHandler().getTagByName(name);
    }

}
