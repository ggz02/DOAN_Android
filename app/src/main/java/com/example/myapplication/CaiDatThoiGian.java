package com.example.myapplication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CaiDatThoiGian {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public static Date HK1_REG_START;
    public static Date HK1_REG_END;
    public static Date HK1_REMIND_START;
    public static Date HK1_REMIND_END;

    public static Date HK2_REG_START;
    public static Date HK2_REG_END;
    public static Date HK2_REMIND_START;
    public static Date HK2_REMIND_END;

    public static Date HK3_REG_START;
    public static Date HK3_REG_END;
    public static Date HK3_REMIND_START;
    public static Date HK3_REMIND_END;

    static {
        try {
            HK1_REG_START = sdf.parse("01/11/2023");
            HK1_REG_END = sdf.parse("17/11/2023");
            HK1_REMIND_START = sdf.parse("18/11/2023");
            HK1_REMIND_END = sdf.parse("23/11/2023");

            HK2_REG_START = sdf.parse("06/03/2024");
            HK2_REG_END = sdf.parse("22/03/2024");
            HK2_REMIND_START = sdf.parse("23/03/2024");
            HK2_REMIND_END = sdf.parse("28/03/2024");

            HK3_REG_START = sdf.parse("15/07/2024");
            HK3_REG_END = sdf.parse("01/08/2024");
            HK3_REMIND_START = sdf.parse("02/08/2024");
            HK3_REMIND_END = sdf.parse("07/08/2024");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static String formatDateRange(Date startDate, Date endDate) {
        return sdf.format(startDate) + " - " + sdf.format(endDate);
    }
}
