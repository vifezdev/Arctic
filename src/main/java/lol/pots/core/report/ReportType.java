package lol.pots.core.report;

import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ReportType {

    COMBAT_HACKS("Combat Hacks", "&b", Arrays.asList(" &7▪ &fAuto Clicker", " &7▪ &fKill Aura", " &7▪ &fReach"), Material.DIAMOND_SWORD),
    MOVEMENT_HACKS("Movement Hacks", "&b", Arrays.asList(" &7▪ &fSpeed", " &7▪ &fTimer", " &7▪ &fFly"), Material.FEATHER),
    RENDER_HACKS("Render Hacks", "&b", Arrays.asList(" &7▪ &fName Tags", " &7▪ &fRadar", " &7▪ &fX-Ray"), Material.DIAMOND_PICKAXE),
    TOXICITY("Toxicity", "&b", Arrays.asList(" &7▪ &fSuicidal Encouragement", " &7▪ &fRacism", " &7▪ &fHomophobia"), Material.SIGN),
    INAPPROPRIATE_NAME("Inappropriate Name", "&b", Arrays.asList(" &7▪ &fInappropriate Username"), Material.NAME_TAG),
    ADVERTISING("Advertising", "&b", Arrays.asList(" &7▪ &fAdvertising Servers", " &7▪ &fAdvertising Videos", " &7▪ &fAdvertising Scams"), Material.PAPER),
    THREATS("Threats", "&b", Arrays.asList(" &7▪ &fThreatening Personal Information", " &7▪ &fThreatening To DDoS", " &7▪ &fThreatening To Dox"), Material.SKULL_ITEM),
    EXPLOITING_BUGS("Exploiting Bugs", "&b", Arrays.asList(" &7▪ &fExploiting Unfair Advantages", " &7▪ &fExploiting Dupes", " &7▪ &fExploiting Bugs"), Material.TNT),
    GLITCHING("Glitching", "&b", Arrays.asList(" &7▪ &fGlitching Enderpearls", " &7▪ &fBlock Glitching"), Material.ENDER_PEARL),
    PUNISHMENT_EVADING("Punishment Evading", "&b", Arrays.asList(" &7▪ &fBlacklist Evading", " &7▪ &fBan Evading", " &7▪ &fMute Evading"), Material.IRON_BARDING),
    CHAT_ABUSE("Chat Abuse", "&b", Arrays.asList(" &7▪ &fChat Flooding", " &7▪ &fChat Spamming"), Material.BOOK);

    private String reason;
    private String color;
    private List<String> description;
    private Material icon;


    ReportType(String reason, String color, List<String> description, Material icon) {
        this.reason = reason;
        this.color = color;
        this.description = description;
        this.icon = icon;
    }

}
