package com.tvtracker.tools;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class SeriesSearchSuggestion implements SearchSuggestion {
    private String name;
    private int id;
    private static Creator CREATOR;

    public SeriesSearchSuggestion(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getBody() {
        return name;
    }

    @Override
    public int describeContents() {
        return id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
