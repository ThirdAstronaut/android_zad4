package lab.swim.pwr.android_zad4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

public class SettingsActivity extends AppCompatActivity {

    private static boolean switchChecked = false;
    private ConstraintLayout mLayout;
    private TextView nightModeTextView;
    private TextView playRandomTextView;
    private Switch nightModeSwitch;
    private Button playRandomSong;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initFields();

        LayoutInflater li = LayoutInflater.from(this);
        View customView = li.inflate(R.layout.custom_bmi_menu_layout, null);
        ActionBar mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setCustomView(customView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

        ImageButton addContent = customView.findViewById(R.id.back_arrow);
        addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.start(getApplicationContext(), getCurrentTheme());
                finish();
            }
        });

        nightModeSwitch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                switchChecked = !switchChecked;
                if (getCurrentTheme().equals("Dark"))
                    setDarkTheme();
                 else
                    setLightTheme();
            }
        });
        nightModeSwitch.setChecked(switchChecked);
        if (getCurrentTheme().equals("Dark")) {
            setDarkTheme();
        } else {
            setLightTheme();
        }

        playRandomSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int id = random.nextInt(20);
                Song song = SongsKeeper.getSongsList().get(id);
                Intent intent = new Intent("song-changed");
                intent.putExtra("SongName", song.getTitle());
                intent.putExtra("SongID", song.getId());
                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setDarkTheme() {
        mLayout.setBackgroundResource(R.color.listDark);
        playRandomSong.setBackgroundResource(R.color.listDark);
        playRandomSong.setTextColor(Color.parseColor("#9dae9a"));
        nightModeTextView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        playRandomTextView.setTextColor(getResources().getColor(R.color.colorPrimary, null));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setLightTheme() {
        mLayout.setBackgroundResource(R.color.listLight);
        playRandomTextView.setTextColor(getResources().getColor(R.color.myCustomDarkFontColor, null));
        nightModeTextView.setTextColor(getResources().getColor(R.color.myCustomDarkFontColor, null));
        playRandomSong.setBackgroundColor(Color.parseColor("#D6D7D7"));
        playRandomSong.setTextColor(Color.parseColor("#000000"));

    }

    private String getCurrentTheme() {
        if (!switchChecked) {
            return "Light";
        } else {
            return "Dark";
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.start(getApplicationContext(), getCurrentTheme());
        finish();
    }

    private void initFields() {
        nightModeSwitch = findViewById(R.id.nightModeSwitch);
        playRandomSong = findViewById(R.id.playRandomSongButton);
        mLayout = findViewById(R.id.settingsActivityConstraintLayout);
        playRandomTextView = findViewById(R.id.playRandomSongTextView);
        nightModeTextView = findViewById(R.id.nightModeTextView);
    }
}
