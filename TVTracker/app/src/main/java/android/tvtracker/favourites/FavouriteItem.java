package android.tvtracker.favourites;

/**
 * Created by Jacek on 13.04.2017.
 */

public class FavouriteItem {
    public int id;

    public String name;

    public String imageUrl;

    public FavouriteItem(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
