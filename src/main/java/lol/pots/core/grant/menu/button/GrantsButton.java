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

@AllArgsConstructor
public class GrantsButton extends Button {

    private Profile target;
    private Grant grant;

    @Override
    public ItemStack getButtonItem(Player player) {
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(" &7▪ &fAdded Rank: &r" + (grant.getRank().getColor() + grant.getAddedRank()));
        lore.add(" &7▪ &fAdded By: &r" + grant.getAddedBy());
        lore.add(" &7▪ &fAdded Duration: &b" + grant.getAddedDurationText());
        lore.add(" &7▪ &fAdded Reason: &b" + grant.getAddedReason());
        if (!grant.isExpired() && !grant.isPardoned() && !grant.getExpiresInDurationText().equals("forever")) {
            lore.add(" &7▪ &fExpires In: &b" + grant.getExpiresInDurationText());
        }
        lore.add("");
        if (!grant.isExpired() && !grant.isPardoned()) {
            Rank defaultRank = Arctic.getInstance().getRankHandler().getDefaultRank();
            if(grant.getAddedBy().toLowerCase().contains("console"))
            {
                lore.add(" &cYou can't remove this grant!");

            }
            else{
                if (grant.getAddedRank().equalsIgnoreCase(defaultRank.getName()) && Arctic.getInstance().getProfileHandler().getProfileByUsername(grant.getAddedBy()).getActiveGrant().getRank().getWeight() > profile.getActiveGrant().getRank().getWeight()) {
                    lore.add(" &cYou can't remove this grant!");
                } else {
                    lore.add(" &aClick to remove!");
                }}
        } else if (grant.isExpired()) {
            lore.add("&bGrant Expired");
            lore.add(" &7▪ &fExpired on: &c" + grant.getExpiredDateText());
        } else if (grant.isPardoned()) {
            lore.add("&bGrant Removed");
            lore.add("");
            lore.add(" &7▪ &fRemoved By: &r" + grant.getPardonedBy());
            lore.add(" &7▪ &ffRemoved On: &c" + grant.getPardonedOnText());
            lore.add(" &7▪ &ffRemoved Reason: &c" + grant.getPardonedReason());
        }
        String name = "&7[#" + grant.getId() + "] &b" + grant.getAddedOnText();
        return new ItemBuilder(Material.WOOL).name(name).lore(lore).durability(grant.getRank().getDurability()).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (!player.hasPermission("arctic.grants.pardon")) {
            playFail(player);
            return;
        }
        if  (grant.getId() == 1) {
            playFail(player);
            return;
        }
        if (grant.isExpired() || grant.isPardoned()) {
            playFail(player);
            return;
        }
        Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        profile.setProcedure(new Procedure(ProcedureType.GRANT_PARDON, ProcedureStage.REQUIRE_REASON, target.getUuid(), grant, null, "", System.currentTimeMillis()));
        TaskUtil.runTaskLater(player::closeInventory, 1L);
        player.sendMessage(CC.translate("&fEnter a reason for pardoning this grant: &7(Type 'cancel' to cancel)"));
        playNeutral(player);
    }
}
