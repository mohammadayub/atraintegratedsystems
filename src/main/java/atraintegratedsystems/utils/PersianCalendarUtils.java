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



    public int[] gregorianToJalali(LocalDate gregorianDate) {
        int gy = gregorianDate.getYear();
        int gm = gregorianDate.getMonthValue();
        int gd = gregorianDate.getDayOfMonth();

        int[] g_d_m = {0,31,28,31,30,31,30,31,31,30,31,30,31};
        int gy2 = (gm > 2) ? (gy + 1) : gy;
        int days = 355666 + (365 * gy) + ((gy2 + 3)/4) - ((gy2 + 99)/100) + ((gy2 + 399)/400) + gd;
        for (int i = 0; i < gm; ++i) {
            days += g_d_m[i];
        }
        int jy = -1595 + (33 * (days / 12053));
        days %= 12053;
        jy += 4 * (days / 1461);
        days %= 1461;
        if (days > 365) {
            jy += (days - 1) / 365;
            days = (days - 1) % 365;
        }
        int jm = (days < 186) ? 1 + days / 31 : 7 + (days - 186) / 30;
        int jd = 1 + ((days < 186) ? (days % 31) : ((days - 186) % 30));

        return new int[]{jy, jm, jd};
    }








}
