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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound = false;
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;


    private TextView titleTextView;
    private TextView songTitleTextView;
    private TextView authorTextView;
    private TextView durationTimeSongRow;
    private Button startButtonSongRow;
    private ImageButton backArrowButton;
    private ImageButton playArrowButton;
    private ImageButton forwardArrowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        mAdapter = new CustomAdapter();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.Callback callback = new SwipeHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        songTitleTextView = findViewById(R.id.songTitleTextView);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("song-changed"));

        backArrowButton = findViewById(R.id.backArrowButton);
        forwardArrowButton = findViewById(R.id.forwardArrowButton);
        playArrowButton = findViewById(R.id.playButton);

        forwardArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicSrv.seek(musicSrv.getPosn() + 10000);
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicSrv.seek(musicSrv.getPosn() -10000);
            }
        });

playArrowButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(musicSrv.isPng()){
            musicSrv.pausePlayer();
        } else
            musicSrv.go();
    }
});
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.N)
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
        String currentSongName = intent.getStringExtra("SongName");
        Long currentID = intent.getLongExtra("SongID", 0);
            songTitleTextView.setText(currentSongName);
            musicSrv.setSong(Math.toIntExact(currentID));
            musicSrv.playSong();
        }
    };

    private ServiceConnection musicConnection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(SongsKeeper.getInstance().getMusicList());
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void songPicked(View view) {
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
    }

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
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.settings_menu:
               /* stopService(playIntent);
                musicSrv = null;
                System.exit(0);*/
                startActivity(new Intent(this, SettingsActivity.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }




}
