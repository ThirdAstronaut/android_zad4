package lab.swim.pwr.android_zad4;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static MusicService musicSrv;
    private static String currentSongName;
    private static String currentTheme = "Light";
    private static Long currentID;

    private Intent playIntent;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mPlayerConstraintLayout;
    private CustomAdapter adapter;
    private TextView songTitleTextView;
    private ImageButton backArrowButton;
    private ImageButton playArrowButton;
    private ImageButton forwardArrowButton;


    public static void start(Context context, String theme) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.putExtra("currentTheme", theme);
        context.startActivity(starter);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentID = savedInstanceState.getLong("songID");
            currentSongName = savedInstanceState.getString("songName");
            currentTheme = savedInstanceState.getString("currentTheme");
        }

        if (getIntent().getStringExtra("currentTheme") != null) {
            currentTheme = getIntent().getStringExtra("currentTheme");
        }

        initViews();

        recyclerViewConfiguration();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("song-changed"));

        buttonsOnClickInit();

        setTheme();

        if (currentSongName != null)
            songTitleTextView.setText(currentSongName);
    }


    protected void onSaveInstanceState(Bundle bundle) {
        if (currentSongName != null && currentID != null) {
            bundle.putString("songName", currentSongName);
            bundle.putString("currentTheme", currentTheme);
            bundle.putLong("songID", currentID);
        }
        super.onSaveInstanceState(bundle);
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.N)
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (musicSrv.isPlaying()) {
                musicSrv.pausePlayer();
                musicSrv.reset();
            }
            currentSongName = intent.getStringExtra("SongName");
            currentID = intent.getLongExtra("SongID", 0);
            songTitleTextView.setText(currentSongName);
            musicSrv.setSong(Math.toIntExact(currentID));
            try {
                musicSrv.playSong();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTheme() {
        if (currentTheme == null || currentTheme.equals("Light")) {
            mPlayerConstraintLayout.setBackgroundResource(R.color.listLight);
            songTitleTextView.setTextColor(getResources().getColor(R.color.myCustomDarkFontColor, null));
            mRecyclerView.setBackgroundResource(R.color.colorPrimaryBottom);
        } else {
            mPlayerConstraintLayout.setBackgroundResource(R.color.listDarkBottom);
            songTitleTextView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
            mRecyclerView.setBackgroundResource(R.color.listDark);
            playArrowButton.setBackgroundResource(R.color.listDarkBottom);
            backArrowButton.setBackgroundResource(R.color.listDarkBottom);
            forwardArrowButton.setBackgroundResource(R.color.listDarkBottom);
        }
        adapter.setCurrentTheme(currentTheme);
    }

    private ServiceConnection musicConnection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicSrv = binder.getService();
            musicSrv.setList(SongsKeeper.getSongsList());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menu:
                AboutActivity.start(getApplicationContext(), currentTheme);
                break;
            case R.id.settings_menu:
                startActivityForResult(new Intent(this, SettingsActivity.class), 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initViews() {
        songTitleTextView = findViewById(R.id.songTitleTextView);
        backArrowButton = findViewById(R.id.backArrowButton);
        forwardArrowButton = findViewById(R.id.forwardArrowButton);
        playArrowButton = findViewById(R.id.playButton);
        adapter = new CustomAdapter();
        mRecyclerView = findViewById(R.id.recyclerView);
        mPlayerConstraintLayout = findViewById(R.id.playerLayout);

    }

    private void recyclerViewConfiguration() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
    }

    private void buttonsOnClickInit() {

        forwardArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicSrv.seek(musicSrv.getPosition() + 10000);
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicSrv.seek(musicSrv.getPosition() - 10000);
            }
        });

        playArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicSrv.isPlaying()) {
                    musicSrv.pausePlayer();
                } else
                    musicSrv.go();
            }
        });
    }
}
