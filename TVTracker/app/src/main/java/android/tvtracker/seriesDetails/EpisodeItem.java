package android.tvtracker.seriesDetails;

public class EpisodeItem {
    public int id;
    public String title;
    public boolean seen;

    public EpisodeItem(int id, String title, boolean seen) {
        this.id = id;
        this.title = title;
        this.seen = seen;
    }
}
