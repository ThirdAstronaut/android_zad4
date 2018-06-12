package lab.swim.pwr.android_zad4;

public class Song {
    private Long mId;
    private String mTitle;
    private String mAuthor;
    private String mDuration;

    Song(Long id, String title, String author, String duration) {
        mTitle = title;
        mAuthor = author;
        mDuration = duration;
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDuration() {
        return mDuration;
    }

    public Long getId() {
        return mId;
    }
}
