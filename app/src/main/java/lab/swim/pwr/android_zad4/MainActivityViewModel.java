package lab.swim.pwr.android_zad4;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Song>> songs;
}
