package jay.messenger.spare.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by leejaebeom on 2017. 8. 17..
 */

public class DateFormat {
    private final static java.text.DateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

    public static Date getDate(String date) throws ParseException {
        return timeStampFormat.parse(date);
    }

    public static String getTimeStampFormat(Date date) {
        return timeStampFormat.format(date);
    }
}
