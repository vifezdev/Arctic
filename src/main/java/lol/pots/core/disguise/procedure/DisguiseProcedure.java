package lol.pots.core.disguise.procedure;

import lombok.Getter;
import lombok.Setter;
import lol.pots.core.disguise.DisguiseProfile;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.MojangUtil;
import xyz.haoshoku.nick.api.NickAPI;

@Getter
@Setter
public class DisguiseProcedure {
    private Profile profile;
    private String name;
    private String skin;
    private Rank rank;

    public DisguiseProcedure(Profile profile) {
        this.profile = profile;
    }

    public void send() {
        final String[] property = MojangUtil.getSkinFromName(skin);
        DisguiseProfile disguiseProfile = new DisguiseProfile(name, rank, property[0], property[1]);

        profile.setDisguiseProfile(disguiseProfile);
        NickAPI.nick(profile.getPlayer(), name);
        NickAPI.setSkin(profile.getPlayer(), skin);
        NickAPI.setUniqueId(profile.getPlayer(), name);
        NickAPI.setGameProfileName(profile.getPlayer(), name);
        NickAPI.refreshPlayer(profile.getPlayer());
        profile.getPlayer().closeInventory();
    }

}
