package lol.pots.core.grant.menu.button;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.grant.Grant;
import lol.pots.core.procedure.Procedure;
import lol.pots.core.procedure.ProcedureStage;
import lol.pots.core.procedure.ProcedureType;
import lol.pots.core.profile.Profile;
import lol.pots.core.rank.Rank;
import lol.pots.core.util.CC;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.TaskUtil;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class GrantButton extends Button {

    private UUID target;
    private Rank rank;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" &7▪ &fWeight: &b" + rank.getWeight());
        lore.add(" &7▪ &fPrefix: " + rank.getPrefix());
        lore.add("");
        lore.add(" &aClick to grant!");
        return new ItemBuilder(Material.WOOL).name(rank.getColor() + rank.getName()).lore(lore).durability(rank.getDurability()).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (!player.hasPermission("arctic.grants.add")) {
            playFail(player);
            return;
        }
        Profile targetProfile = Arctic.getInstance().getProfileHandler().getProfileByUUID(target);
        if (targetProfile.getActiveGrant().getRank().getName().equalsIgnoreCase(rank.getName())) {
            playFail(player);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        String addedBy = profile.getActiveGrant().getRank().getColor() + profile.getUsername();
        long addedOn = System.currentTimeMillis();
        Grant grant = new Grant(targetProfile.getGrants().size() + 1, rank.getName(), addedBy, addedOn, addedOn, "", false, "", "", addedOn);
        profile.setProcedure(new Procedure(ProcedureType.GRANT_ADD, ProcedureStage.REQUIRE_DURATION, target, grant, null, "", System.currentTimeMillis()));

        TaskUtil.runTaskLater(player::closeInventory, 1L);
        player.sendMessage(CC.translate("&fEnter a duration for adding this grant: &7(Type 'cancel' to cancel)"));
        playNeutral(player);
    }
}
