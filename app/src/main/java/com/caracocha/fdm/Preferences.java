package com.caracocha.fdm;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Created by xabi on 6/28/15.
 */

public class Preferences extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    public static final String KEY_PREF_SHARE = "pref_key_share";
    public static final String KEY_PREF_VERSION = "pref_key_version";
    private static String DEBUG_TAG = "PREFERENCES";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preferences);
        findPreference("pref_key_share").setOnPreferenceClickListener(this);
        try {
            String sVersion = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            findPreference("pref_key_version").setSummary(sVersion);
        }
        catch (PackageManager.NameNotFoundException e) {
            findPreference("pref_key_version").setSummary("Error");
            Log.d(DEBUG_TAG, "Error when getting the app version");
            e.printStackTrace();
        }
        return;
    }

    public boolean onPreferenceChange(Preference preference, Object obj) {
        return false;
    }

    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("pref_key_share")) {
            Intent i = new Intent();
            i.setAction("android.intent.action.SEND");
            i.setType("text/plain");
            i.putExtra("android.intent.extra.TEXT", getString(R.string.pref_share_content));
            startActivity(Intent.createChooser(i, getString(R.string.pref_title_share)));
            return true;
        } else
        {
            return false;
        }
    }

}