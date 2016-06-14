package com.caracocha.fdm.domain.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xabi@crespum.eu on 6/13/16.
 */
public class Event {
    private static final String TAG = Event.class.getSimpleName();

    // Field to be (de)serialized
    private String EVENT_NAME;
    private String DATE;
    private String START_TIME;
    private String END_TIME;
    private String CATEGORY;
    private String LOCATION;
    private String LATITUDE;
    private String LONGITUDE;
    private String DESCRIPTION;
    private String PRICE;
    private String IMG_URL;
    private String DETAILS_URL;

    private String sItemDay; // e.g. Luns 2
    private String sWeekDay; // Luns, martes... (Galician)
    private int iDay; // Day of the month (1-31)
    private String sMonth; // Xaneiro, Febreiro... (Galician)
    private int iMonth; // Month number (0-11)
    private int iYear; // Year number
    private String sMonthAndYear; // e.g. Xaneiro 2015

    private String sValidateURL(String sURL) {
        if (sURL != null && !sURL.startsWith("http://") && !sURL.startsWith("https://"))
            return "http://" + sURL;
        else return sURL;
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
            Log.d(TAG, "Error parsing a date: " + sDate);
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Get the month name from parsing sDate
     *
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
                Log.e(TAG, "Wrong month number: " + c.get(Calendar.MONTH));
                return "Error";
        }
    }

    /**
     * Get the month name from parsing a calendar
     *
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
                Log.e(TAG, "Wrong week day number: " + c.get(Calendar.DAY_OF_WEEK));
                return "Error";
        }
    }
}
