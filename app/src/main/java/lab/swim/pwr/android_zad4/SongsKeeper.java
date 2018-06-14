package lab.swim.pwr.android_zad4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafał on 2018-03-16.
 */

class SongsKeeper {

    public static List<Song> getSongsList() {
        String[] titles = {"Bohemian Rhapsody", "Stairway to Heaven", "Muscle Museum", "Again", "Supremacy", "Faint", "The Unforgiven", "Smoke On The Water",
                "Nothing Else Matters", "Epitaph", "Paranoid", "Smells Like Teen Spirit", "Whiskey In The Jar", "Kashmir", "Can't Stop", "Chop Suey"};
        String[] authors = {"Queen", "Led Zeppelin", "Muse", "Archive", "Muse", "Linkin Park", "Metallica", "Deep Purple", "Metallica", "King Crimson", "Black Sabbath",
                "Nirvana", "Metallica", "Led Zeppelin", "Red Hot Chili Peppers", "System of a Down"};
        String[] duration = {"05:56", "08:02", "04:22", "16:17", "04:55", "02:42", "06:27", "05:43", "06:30", "08:52", "02:50", "05:01", "05:04", "08:29", "04:26", "03:30"};
        List<Song> songsList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            songsList.add(new Song((long) i, titles[i], authors[i], duration[i]));
        }
        return songsList;
    }
}
