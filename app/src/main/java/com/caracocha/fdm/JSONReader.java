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
    private static final String TAG_DAY = "DAY";
    private static final String TAG_START_TIME = "START_TIME";
    private static final String TAG_END_TIME = "END_TIME";
    private static final String TAG_CATEGORY = "CATEGORY";
    private static final String TAG_PLACE = "PLACE";
    private static final String TAG_LATITUDE = "LATITUDE";
    private static final String TAG_LONGITUDE = "LONGITUDE";
    private static final String TAG_DESCRIPTION = "DESCRIPTION";

    private ArrayList alEvents;
    private Context context;
    JSONArray events;
    onJSONDownloaded ifJSON;
    private ProgressDialog pDialog;
    private String sJSONStr;
    private String sURL;

    interface onJSONDownloaded {
        void updateLayout();
    }

    public JSONReader(Context context, onJSONDownloaded ifJSON, String sURL, ArrayList alEvents) {
        this.context = context;
        this.sURL = sURL;
        this.alEvents = alEvents;
        this.ifJSON = ifJSON;
    }

    public JSONReader(Context context, String sURL, ArrayList alEvents) {
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
        String sEventName;
        String sDay;
        String sStartTime;
        String sEndTime;
        String sCategory;
        String sDescription;
        String sPlace;
        String sLatitude;
        String sLongitude;

        JSONObject obj;

        try {
            events = new JSONArray(sJSON);
        } catch (JSONException e) {
            e.printStackTrace();
            alEvents.add(new Item(Item.INFO, context.getResources().getString(R.string.JSON_not_found)));
            return;
        }

        if (events.length() == 0) {
            alEvents.add(new Item(Item.INFO, context.getResources().getString(R.string.no_events_found)));
        }

        for(int i = 0; i<events.length(); i++) {
            try {
                obj = events.getJSONObject(i);
                sEventName = obj.getString(JSONReader.TAG_EVENT_NAME);
                sDay = obj.getString(JSONReader.TAG_DAY);
                sStartTime = obj.getString(JSONReader.TAG_START_TIME);
                sEndTime = obj.getString(JSONReader.TAG_END_TIME);
                sCategory = obj.getString(JSONReader.TAG_CATEGORY);
                sPlace = obj.getString(JSONReader.TAG_PLACE);
                sLatitude = obj.getString(JSONReader.TAG_LATITUDE);
                sLongitude = obj.getString(JSONReader.TAG_LONGITUDE);
                sDescription = obj.getString(JSONReader.TAG_DESCRIPTION);

                alEvents.add(new Item(Item.DATE, sDay));
                alEvents.add(new Item(sEventName, sDay, sStartTime, sEndTime, sCategory, sPlace, sLatitude, sLongitude, sDescription));

                if (i != events.length() - 1) {
                    sEventName = events.getJSONObject(i + 1).getString("DAY");
                    if (!sDay.equals(sEventName)) {
                        alEvents.add(new Item(Item.DATE, sEventName));
                    }
                }
            } catch (JSONException e) {
                Log.e(JSONReader.DEBUG_TAG, "Error in getJSONObject\n" + e);
            }
        }
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
            Log.d(JSONReader.DEBUG_TAG, "Response: > ".concat(sJSONStr));
        } catch (IOException e) {
            Log.e(JSONReader.DEBUG_TAG, "IOException" + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(JSONReader.DEBUG_TAG, "Error converting result " + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        //super.onPostExecute(v);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        ifJSON.updateLayout();
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
