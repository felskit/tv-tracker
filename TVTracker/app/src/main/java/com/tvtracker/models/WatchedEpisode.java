package com.tvtracker.models;

public class WatchedEpisode {
    public int userId;
    public int id;
    public Boolean watched;

    public WatchedEpisode(int userId, int id, Boolean watched) {
        this.userId = userId;
        this.id = id;
        this.watched = watched;
    }
}
