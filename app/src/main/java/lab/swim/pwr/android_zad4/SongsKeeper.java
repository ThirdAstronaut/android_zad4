package lab.swim.pwr.android_zad4;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafał on 2018-03-16.
 */

public class SongsKeeper {
    private static SongsKeeper instance = null;
    private List<Song> mSongsList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private SongsKeeper() {
        mSongsList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mSongsList.add(new Song((long) i, "Title " + i, "author "+i, i+":0"+(i%10))); /*String.valueOf(ThreadLocalRandom.current().nextInt(1000)*/;
        }//Long id, String title, String author, String duration
    }

    public List<Song> getMusicList() {
        return mSongsList;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static SongsKeeper getInstance() {
        if (instance == null) {
            instance = new SongsKeeper();
        }
        return instance;
    }
}
