package eu.epitech.todolist.Utils;

import java.util.Calendar;

public class Utils {

    public static boolean compareDate(String taskDate)
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String[] calend = taskDate.split("/");

        if (mYear > Integer.parseInt(calend[2])) {
            return true;
        } else if (mMonth > Integer.parseInt(calend[1])) {
            return true;
        } else if (mDay >= Integer.parseInt(calend[0]))
            return true;
        return false;
    }

    public static boolean compareTime(String taskTime) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String[] time = taskTime.split(":");

        if (hour >= Integer.parseInt(time[0])) {
            if (minute >= Integer.parseInt(time[1])) {
                return true;
            }
            return true;
        }
        return false;
    }

    public static String fixedHoursTime(String timeTask) {
        String[] time = timeTask.split(":");
        String fixHours = time[0];
        String fixMinutes = time[1];

        if (time[0].length() == 1) {
            fixHours = "0" + time[0];
        }
        if (time[1].length() == 1) {
            fixMinutes = "0" + time[1];
        }

        return fixHours + ":" + fixMinutes;
    }
}
