package android.tvtracker.home;

public class SeriesCardItem {
    private int mId;
    private String mTitle;
    private String mDescription;
    private String mImageUrl;

    public SeriesCardItem(int id, String title, String description, String imageUrl) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mImageUrl = imageUrl;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}