package lol.pots.core.messages;

import lol.pots.core.Arctic;
import lol.pots.core.util.CC;

public class ErrorMessage {

    public static String IN_GAME_COMMAND_ONLY = CC.translate(Arctic.getInstance().getMsgConfig().getString("ERROR-MESSAGES.IN-GAME-COMMAND-ONLY"));
    ;
    public static String PROFILE_NOT_FOUND = CC.translate(Arctic.getInstance().getMsgConfig().getString("ERROR-MESSAGES.PROFILE-NOT-FOUND"));
    public static String RANK_NOT_FOUND = CC.translate(Arctic.getInstance().getMsgConfig().getString("ERROR-MESSAGES.RANK-NOT-FOUND"));
    public static String PREFIX_NOT_FOUND = CC.translate(Arctic.getInstance().getMsgConfig().getString("ERROR-MESSAGES.PREFIX-NOT-FOUND"));


}
