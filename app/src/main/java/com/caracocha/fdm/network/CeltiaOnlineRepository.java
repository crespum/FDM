package com.caracocha.fdm.network;

import android.os.AsyncTask;
import android.util.Log;

import com.caracocha.fdm.domain.model.Event;
import com.caracocha.fdm.domain.model.ItemsList;
import com.caracocha.fdm.domain.repository.CeltiaRepository;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by xabi@crespum.eu on 6/13/16.
 */
public class CeltiaOnlineRepository implements CeltiaRepository {
    private static final String TAG = CeltiaOnlineRepository.class.getSimpleName();

    public static final String JSON_LINK = "https://www.dropbox.com/s/ekflrytyix82z7g/Eventos.txt?dl=1";

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

    @Override
    public ItemsList retrieveEventsList() {
        String json = requestJSON();
        return getDevicesList(json);
    }

    @Override
    public Event retrieveEvent(int id) {
        return null;
    }

    protected String requestJSON() {
        String json = null;

        try {
            URL url = new URL(JSON_LINK);
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
            json = sBuilder.toString();
            Log.d(TAG, "Received JSON > ".concat(json));

        } catch (IOException e) {
            Log.e(TAG, "IOException " + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "Error " + e.toString());
            e.printStackTrace();
        }

        return json;
    }

    /**
     * Reads the JSON file containing the list of the events and returns a Java native type.
     * @return DevicesList containing a set of {@link ItemsList}.
     */
    public ItemsList getDevicesList(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        gsonBuilder.registerTypeAdapter(ItemsList.class, new ItemsList());
        Gson gson = gsonBuilder.create();

        Type listType = new TypeToken<ItemsList>() {}.getType();
        return gson.fromJson(json, listType);
    }
}
