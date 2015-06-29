package com.caracocha.fdm;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xabi on 6/20/15.
 */
public class Item {
    private static String DEBUG_TAG = "ITEM";

    public static final String DAY = "DAY";
    public static final int iDAY = 0;
    public static final String INFO = "INFO";
    public static final int iINFO = 1;
    public static final String EVENT = "EVENT";
    public static final int iEVENT = 2;
    public static final String AD = "AD";
    public static final int iAD = 3;

    String sType;
    String sTitle;
    String sDate;
    String sStartTime;
    String sEndTime;
    String sCategory;
    String sPlace;
    String sLatitude;
    String sLongitude;
    String sDescription;

    String sImgURL;
    String sEventURL;

    String sItemDay; // e.g. Luns 2
    String sWeekDay; // Luns, martes... (Galician)
    int iDay; // Day of the month (1-31)
    String sMonth; // Xaneiro, Febreiro... (Galician)
    int iMonth; // Month number (0-11)
    int iYear; // Year number

    String sMessage;

    public Item(String sType, String sMessage) {
        this.sType = sType;
        if (sType.equals(Item.DAY)) {
            Calendar c = parseDate(sMessage);
            this.sMessage = getDay(c) + " " + c.get(Calendar.DAY_OF_MONTH);
        } else {
            this.sMessage = sMessage;
        }
    }

    public Item(String sTitle, String sDate, String sStartTime, String sEndTime, String sCategory,
                String sPlace, String sLatitude, String sLongitude, String sDescription) {
        this.sType = Item.EVENT;
        this.sTitle = sTitle;
        this.sDate = sDate;
        this.sStartTime = sStartTime;
        this.sEndTime = sEndTime;
        this.sCategory = sCategory;
        this.sLatitude = sLatitude;
        this.sLongitude = sLongitude;
        this.sDescription = sDescription;
        this.sPlace = sPlace;

        Calendar c = parseDate(sDate);
        sWeekDay = getDay(c);
        iDay = c.get(Calendar.DAY_OF_MONTH);
        sMonth = getMonth(c);
        iYear = c.get(Calendar.YEAR);

        Log.d(DEBUG_TAG, "Day number: " + sWeekDay);
        Log.d(DEBUG_TAG, "Day: " + iDay);
        Log.d(DEBUG_TAG, "Month: " + sMonth);
        Log.d(DEBUG_TAG, "Year: " + iYear);

    }


    /**
     * @param sDate has the form dd/mm/yyyy
     * @return Calendar object containing the date
     */
    private Calendar parseDate(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(sDate));
        } catch (ParseException e) {
            Log.d(DEBUG_TAG, "Error parsing a date: " + sDate);
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Get the month name from parsing sDate
     * @param c Calendar with the date to be parsed
     */
    private String getMonth(Calendar c) {
        iMonth = c.get(Calendar.MONTH);
        switch (iMonth) {
            case Calendar.JANUARY:
                return "Xaneiro";
            case Calendar.FEBRUARY:
                return "Febreiro";
            case Calendar.MARCH:
                return "Marzo";
            case Calendar.APRIL:
                return "Abril";
            case Calendar.MAY:
                return "Maio";
            case Calendar.JUNE:
                return "Xuño";
            case Calendar.JULY:
                return "Xullo";
            case Calendar.AUGUST:
                return "Agosto";
            case Calendar.SEPTEMBER:
                return "Setembro";
            case Calendar.OCTOBER:
                return "Outubro";
            case Calendar.NOVEMBER:
                return "Novembro";
            case Calendar.DECEMBER:
                return "Decembro";
            default:
                Log.d(DEBUG_TAG, "Wrong month number: " + c.get(Calendar.MONTH));
                return "Error";
        }
    }

    /**
     * Get the month name from parsing a calendar
     * @param c Calendar with the date to be parsed
     */
    private String getDay(Calendar c) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "Luns";
            case Calendar.TUESDAY:
                return "Martes";
            case Calendar.WEDNESDAY:
                return "Mércores";
            case Calendar.THURSDAY:
                return "Xoves";
            case Calendar.FRIDAY:
                return "Venres";
            case Calendar.SATURDAY:
                return "Sábado";
            case Calendar.SUNDAY:
                return "Domingo";
            default:
                Log.d(DEBUG_TAG, "Wrong week day number: " + c.get(Calendar.DAY_OF_WEEK));
                return "Error";
        }
    }
}