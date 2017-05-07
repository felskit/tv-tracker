package com.tvtracker.models;

import java.util.ArrayList;

public class Show {
    public int id;
    public String name;
    public String image;
    public String summary;
    public String premiered;
    public String genres;
    public String status;
    public String network;
    public short runtime;
    public float rating;
    public String source;
    public ArrayList<ShowEpisode> episodes;
}
