package android.tvtracker.seriesDetails;

/**
 * Created by Jacek on 14.04.2017.
 */

public class Episode {
    public int id;
    public String title;
    public boolean seen;

    public Episode(int id, String title, boolean seen) {
        this.id = id;
        this.title = title;
        this.seen = seen;
    }
}
