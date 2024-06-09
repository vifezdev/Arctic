package lol.pots.core.report.menu.button;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import lol.pots.core.Arctic;
import lol.pots.core.handler.ProfileHandler;
import lol.pots.core.packet.BroadcastReportPacket;
import lol.pots.core.profile.Profile;
import lol.pots.core.report.ReportType;
import lol.pots.core.util.CC;
import lol.pots.core.util.ItemBuilder;
import lol.pots.core.util.TaskUtil;
import lol.pots.core.util.menu.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ReportButton extends Button {

    ProfileHandler profileHandler = Arctic.getInstance().getProfileHandler();

    private ReportType type;
    private UUID target;

    public ReportButton(ReportType type, UUID target) {
        this.type = type;
        this.target = target;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.addAll(type.getDescription());
        lore.add("");
        lore.add(" &aClick to submit!");
        return new ItemBuilder(type.getIcon()).name(type.getColor() + type.getReason()).lore(lore).build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Profile profile = profileHandler.getProfileByUUID(player.getUniqueId());
        Profile targetProfile = profileHandler.getProfileByUUID(target);
        profile.setLastReportOn(System.currentTimeMillis());
        profile.save();
        player.sendMessage(CC.translate("&aYou've submitted a report to all online staff members."));
        String message = Arctic.getInstance().getMsgConfig().getString("STAFF.REPORT.MESSAGE");
        BroadcastReportPacket packet = new BroadcastReportPacket(CC.translate(message
                .replace("{server}", Bukkit.getServerName())
                .replace("{profile}", profile.getActiveGrant().getRank().getColor() + player.getName())
                .replace("{target}", targetProfile.getActiveGrant().getRank().getColor() + targetProfile.getUsername())
                .replace("{reason}", type.getReason())));
        Arctic.getInstance().getRedisHandler().sendPacket(packet);
        playNeutral(player);
        TaskUtil.runTaskLater(player::closeInventory, 1L);
    }
}
