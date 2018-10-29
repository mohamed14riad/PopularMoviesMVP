package com.mohamed14riad.popularmoviesmvp.mvp.sorting;

import android.content.Context;
import android.content.SharedPreferences;

public class SortingOptionStore {

    private SharedPreferences pref;
    private static final String SELECTED_OPTION = "selectedOption";
    private static final String PREF_NAME = "SortingOptionStore";

    public SortingOptionStore(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setSelectedOption(String selectedOption) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SELECTED_OPTION, selectedOption);
        editor.apply();
    }

    public String getSelectedOption() {
        return pref.getString(SELECTED_OPTION, SortType.MOST_POPULAR);
    }

}
