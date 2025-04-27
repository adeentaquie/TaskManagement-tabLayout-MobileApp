package com.example.taskmanagement_tablayout_ass3;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "UserProfilePrefs";

    // Keys for Profile fields
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DOB = "dob";
    private static final String KEY_THEME = "theme"; // light or dark

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // Save Methods
    public void saveFirstName(String firstName) {
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.apply();
    }

    public void saveLastName(String lastName) {
        editor.putString(KEY_LAST_NAME, lastName);
        editor.apply();
    }

    public void savePhone(String phone) {
        editor.putString(KEY_PHONE, phone);
        editor.apply();
    }

    public void saveGender(String gender) {
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public void saveDOB(String dob) {
        editor.putString(KEY_DOB, dob);
        editor.apply();
    }

    public void saveTheme(String theme) {
        editor.putString(KEY_THEME, theme);
        editor.apply();
    }

    // Get Methods
    public String getFirstName() {
        return prefs.getString(KEY_FIRST_NAME, "Not Set");
    }

    public String getLastName() {
        return prefs.getString(KEY_LAST_NAME, "Not Set");
    }

    public String getPhone() {
        return prefs.getString(KEY_PHONE, "Not Set");
    }

    public String getGender() {
        return prefs.getString(KEY_GENDER, "Not Set");
    }

    public String getDOB() {
        return prefs.getString(KEY_DOB, "Not Set");
    }

    public String getTheme() {
        return prefs.getString(KEY_THEME, "light"); // default light
    }
}
