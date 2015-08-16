package com.caracocha.fdm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Created by xabi on 6/20/15.
 */
public class JSONReader extends AsyncTask<String, Integer, Void> {

    private static final String DEBUG_TAG = "ReaderJSON";

    private static final String TAG_EVENT_NAME = "EVENT_NAME";
    private static final String TAG_DAY = "DAY";  // Actually a date with format dd/MM/yyyy
    private static final String TAG_START_TIME = "START_TIME";
    private static final String TAG_END_TIME = "END_TIME";
    private static final String TAG_CATEGORY = "CATEGORY";
    private static final String TAG_PLACE = "PLACE";
    private static final String TAG_LATITUDE = "LATITUDE";
    private static final String TAG_LONGITUDE = "LONGITUDE";
    private static final String TAG_DESCRIPTION = "DESCRIPTION";
    // New fields
    private static final String TAG_PRICE = "PRICE";
    private static final String TAG_IMG_URL = "IMG_URL";
    private static final String TAG_URL = "URL";
    private static final String TAG_TYPE = "TYPE"; // EVENT, INFO or AD

    private ArrayList alEvents;
    private Context context;
    JSONArray events;
    private ProgressDialog pDialog;
    private String sJSONStr;
    private String sURL;

    onJSONDownloadListener ifJSON;

    interface onJSONDownloadListener {
        void onJSONDownload();
    }

    public JSONReader(Context context, onJSONDownloadListener ifJSON, String sURL, ArrayList alEvents) {
        this.ifJSON = ifJSON;
        this.context = context;
        this.sURL = sURL;
        this.alEvents = alEvents;
    }

    /**
     * Parses the JSON string and creates an ArrayList containing all the events that will be
     * displayed in the mail list.
     *
     * @param sJSON
     */
    private void vReadJSONObject(String sJSON) {
        String sType;
        String sTitle;
        String sDate;
        String sStartTime;
        String sEndTime;
        String sCategory;
        String sDescription;
        String sPlace;
        String sLatitude;
        String sLongitude;
        String sNextDate;
        String sPrice;
        String sImgURL;
        String sURL;

        JSONObject obj;

        try {
            events = new JSONArray(sJSON);
        } catch (JSONException e) {
            e.printStackTrace();
            alEvents.add(new Item(Item.INFO, context.getResources().getString(R.string.JSON_error)));
            return;
        }

        if (events.length() == 0) {
            alEvents.add(new Item(Item.INFO, context.getResources().getString(R.string.no_events_found)));
            return;
        }

        Log.d(DEBUG_TAG, "Number of events:  " + events.length());
        try {
            // The first card indicates the  MONTH and the second the DAY
            //alEvents.add(new Item(Item.MONTH, events.getJSONObject(0).getString(JSONReader.TAG_DAY)));
            alEvents.add(new Item(Item.DAY, events.getJSONObject(0).getString(JSONReader.TAG_DAY)));

            for (int i = 0; i < events.length(); i++) {
                obj = events.getJSONObject(i);
                // if : else (shortcut)
                sType = (obj.has(JSONReader.TAG_TYPE)) ? obj.getString(JSONReader.TAG_TYPE) : null;
                sTitle = (obj.has(JSONReader.TAG_EVENT_NAME)) ? obj.getString(JSONReader.TAG_EVENT_NAME) : null;
                sDate = (obj.has(JSONReader.TAG_DAY)) ? obj.getString(JSONReader.TAG_DAY) : null;
                sStartTime = (obj.has(JSONReader.TAG_START_TIME)) ? obj.getString(JSONReader.TAG_START_TIME) : null;
                sEndTime = (obj.has(JSONReader.TAG_END_TIME)) ? obj.getString(JSONReader.TAG_END_TIME) : null;
                sCategory = (obj.has(JSONReader.TAG_CATEGORY)) ? obj.getString(JSONReader.TAG_CATEGORY) : null;
                sPlace = (obj.has(JSONReader.TAG_PLACE)) ? obj.getString(JSONReader.TAG_PLACE) : null;
                sLatitude = (obj.has(JSONReader.TAG_LATITUDE)) ? obj.getString(JSONReader.TAG_LATITUDE) : null;
                sLongitude = (obj.has(JSONReader.TAG_LONGITUDE)) ? obj.getString(JSONReader.TAG_LONGITUDE) : null;
                sDescription = (obj.has(JSONReader.TAG_DESCRIPTION)) ? obj.getString(JSONReader.TAG_DESCRIPTION) : null;
                sPrice = (obj.has(JSONReader.TAG_PRICE)) ? obj.getString(JSONReader.TAG_PRICE) : null;
                sImgURL = (obj.has(JSONReader.TAG_IMG_URL)) ? obj.getString(JSONReader.TAG_IMG_URL) : null;
                sURL = (obj.has(JSONReader.TAG_URL)) ? obj.getString(JSONReader.TAG_URL) : null;

                if (sType.equals(Item.EVENT)) {
                    Log.d(DEBUG_TAG, "New event: " + sTitle);
                    alEvents.add(new Item(sTitle, sDate, sStartTime, sEndTime, sCategory,
                            sPlace, sLatitude, sLongitude, sDescription, sPrice, sImgURL, sURL));
                } else {
                    Log.d(DEBUG_TAG, "New info item: " + sTitle);
                    alEvents.add(new Item(sType, sTitle, sImgURL, sURL));
                }

                if (i != events.length() - 1) {
                    sNextDate = events.getJSONObject(i + 1).getString("DAY");
                    // Compare month dd/MM/yyyy
                    if (!sDate.substring(3).equals(sNextDate.substring(3))) {
                        alEvents.add(new Item(Item.MONTH, sNextDate));
                    }
                    if (!sDate.equals(sNextDate)) {
                        alEvents.add(new Item(Item.DAY, sNextDate));
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(JSONReader.DEBUG_TAG, "Error in getJSONObject\n" + e);
            alEvents.add(new Item(Item.INFO, context.getResources().getString(R.string.JSON_error)));
        }
        Log.d(DEBUG_TAG, "Length of array list: " + alEvents.size());
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(sURL);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Convert response to string using String Builder
            BufferedReader bReader = new BufferedReader(new InputStreamReader(input, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            input.close();
            sJSONStr = sBuilder.toString();

            // Parse JSON
            vReadJSONObject(sJSONStr);
            //Log.d(JSONReader.DEBUG_TAG, "Response: > ".concat(sJSONStr));
        } catch (IOException e) {
            Log.e(JSONReader.DEBUG_TAG, "IOException" + e.toString());
            e.printStackTrace();
            alEvents.add(new Item(Item.INFO, context.getResources().getString(R.string.JSON_error)));
        } catch (Exception e) {
            Log.e(JSONReader.DEBUG_TAG, "Error " + e.toString());
            e.printStackTrace();
            alEvents.add(new Item(Item.INFO, context.getResources().getString(R.string.JSON_error)));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        //super.onPostExecute(v);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        ifJSON.onJSONDownload();
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getString(R.string.JSON_espera));
        pDialog.setCancelable(false);
        pDialog.show();
    }

}
