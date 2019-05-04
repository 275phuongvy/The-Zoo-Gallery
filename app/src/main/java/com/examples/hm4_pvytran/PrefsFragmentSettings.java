package com.examples.hm4_pvytran;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class PrefsFragmentSettings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public PrefsFragmentSettings() {
    }
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preference from an XML resource
        addPreferencesFromResource(R.xml.prefs_fragment_settings);
    }
    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener when a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        // Set up a click listener for company info
        Preference pref;
        pref = getPreferenceScreen().findPreference("key_animal_info");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick (Preference preference) {
                //Handle action on click here
                try {
                    Uri site = Uri.parse("https://en.wikipedia.org/wiki/List_of_animals_culled_in_zoos");
                    Intent intent = new Intent(Intent.ACTION_VIEW, site);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Log.e("PrefsFragmentSettings", "Browser failed", e);
                }
                return true;
            }
        } );
    }

    public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String key) {
        if (key.equals("key_music_enabled")) {
            boolean b = sharedPreferences.getBoolean("key_music_enabled", true);
            if (b==false) {
                if (Assets.mp != null)
                    Assets.mp.setVolume(0, 0);
            }
            else {
                if (Assets.mp != null)
                    Assets.mp.setVolume(1, 1);
            }
        }
        if (key.equals("key_sound_enabled")) {
            boolean b = sharedPreferences.getBoolean("key_sound_enabled", true);
            if (b==false) {
                if (Assets.sp != null)
                    Assets.sp.setVolume(1, 0, 0);
            }
            else {
                if (Assets.sp != null)
                    Assets.sp.setVolume(1, 1, 1);
            }
        }
    }
}

