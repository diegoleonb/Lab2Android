package org.adaschool.retrofit.storage;

import android.content.SharedPreferences;

public class SharedPreferencesStorage implements Storage{

    final String SHARED_PREFERENCES_FILE_NAME = "my_prefs";
    final String TOKEN_KEY= "token_key";

    private SharedPreferences sharedPreferences;


    public SharedPreferencesStorage(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void saveToken(String token) {
        sharedPreferences.edit()
                .putString(TOKEN_KEY, token)
                .apply();
    }

    @Override
    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY,"");
    }

    @Override
    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
