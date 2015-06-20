package com.caracocha.fdm;

/**
 * Created by xabi on 6/20/15.
 */
public class Item {

    static final String DATE = "0";
    static final String INFO = "1";
    static final String EVENT = "3";

    String sType;
    String sTitle;
    String sDay;
    String sStartTime;
    String sEndTime;
    String sCategory;
    String sPlace;
    String sLatitude;
    String sLongitude;
    String sDescription;

    String sImgURL;

    String sMessage;

    public Item(String sType, String sMessage) {
        this.sType = sType;
        this.sMessage = sMessage;
    }

    public Item(String sTitle, String sDay, String sStartTime, String sEndTime, String sCategory,
                String sPlace, String sLatitude, String sLongitude, String sDescription) {
        this.sType = Item.EVENT;
        this.sTitle = sTitle;
        this.sDay = sDay;
        this.sStartTime = sStartTime;
        this.sEndTime = sEndTime;
        this.sCategory = sCategory;
        this.sLatitude = sLatitude;
        this.sLongitude = sLongitude;
        this.sDescription = sDescription;
        this.sPlace = sPlace;
    }
}