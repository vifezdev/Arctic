package lol.pots.core.util;

import java.text.DateFormat;
import java.util.Date;

public class DateUtil {

    public static String formatDate(long time) {
        return DateFormat.getDateTimeInstance().format(new Date(time));
    }

}
