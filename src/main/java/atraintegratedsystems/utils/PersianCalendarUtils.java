package atraintegratedsystems.utils;

import java.time.LocalDate;

public class PersianCalendarUtils {

    public static LocalDate jalaliToGregorian(int jy, int jm, int jd) {
        int gy;
        int[] g_d_m = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] j_days_in_month = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};

        int jy2 = (jy > 979) ? jy - 979 : jy - 979;
        int jm2 = jm - 1;
        int jd2 = jd - 1;

        int j_day_no = 365 * jy2 + (jy2 / 33) * 8 + ((jy2 % 33 + 3) / 4);
        for (int i = 0; i < jm2; ++i)
            j_day_no += j_days_in_month[i];
        j_day_no += jd2;

        int g_day_no = j_day_no + 79;

        int gy2 = 1600 + 400 * (g_day_no / 146097); /* 146097 = 365*400 + 400/4 - 400/100 + 400/400 */
        g_day_no = g_day_no % 146097;

        boolean leap = true;
        if (g_day_no >= 36525) {
            g_day_no--;
            gy2 += 100 * (g_day_no / 36524); /* 36524 = 365*100 + 100/4 - 100/100 */
            g_day_no = g_day_no % 36524;

            if (g_day_no >= 365)
                g_day_no++;
            else
                leap = false;
        }

        gy2 += 4 * (g_day_no / 1461); /* 1461 = 365*4 + 4/4 */
        g_day_no %= 1461;

        if (g_day_no >= 366) {
            leap = false;

            g_day_no--;
            gy2 += g_day_no / 365;
            g_day_no = g_day_no % 365;
        }

        for (int i = 0; i < 13; i++) {
            int v = g_d_m[i];
            if (i == 2 && leap) v++;
            if (g_day_no < v) {
                return LocalDate.of(gy2, i, g_day_no + 1);
            }
            g_day_no -= v;
        }

        throw new IllegalArgumentException("Invalid Jalali date");
    }
}
